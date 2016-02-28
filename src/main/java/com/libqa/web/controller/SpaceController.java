package com.libqa.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.libqa.application.enums.ActivityType;
import com.libqa.application.framework.ResponseData;
import com.libqa.application.util.LoggedUserManager;
import com.libqa.application.util.StringUtil;
import com.libqa.web.domain.*;
import com.libqa.web.service.common.ActivityService;
import com.libqa.web.service.common.KeywordService;
import com.libqa.web.service.space.SpaceService;
import com.libqa.web.service.user.UserFavoriteService;
import com.libqa.web.service.user.UserService;
import com.libqa.web.service.wiki.WikiService;
import com.libqa.web.view.space.*;
import com.libqa.web.view.wiki.DisplayWiki;
import com.sun.deploy.net.HttpResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

/**
 * @Author : yion
 * @Date : 2015.01.23
 * @Description : 공간 생성 및 조회 controller
 */
@Slf4j
@Controller
public class SpaceController {

    @Value("${howling.hello.message}")
    private String message;

    @Autowired
    private SpaceService spaceService;

    @Autowired
    private KeywordService keywordService;

    @Autowired
    private WikiService wikiService;

    @Autowired
    private ActivityService activityService;

    @Autowired
    private UserFavoriteService userFavoriteService;

    @Autowired
    private UserService userService;


    @Autowired
    private LoggedUserManager loggedUserManager;

    /**
     * 파일 업로드 테스트용 페이지
     * @param model
     * @return
     */
    @Deprecated
    @RequestMapping("/space/fileUpload")
    public ModelAndView fileUpload(Model model) {
        ModelAndView mav = new ModelAndView("/space/ajaxUpload");
        return mav;
    }

    @RequestMapping("/space")
    public String space() {
        return "redirect:/space/main";
    }

    @RequestMapping("/space/main")
    public ModelAndView spaceMain(HttpServletRequest request) {
        log.debug("## /main");
        ModelAndView mav = new ModelAndView("/space/main");

        // 전체 space 목록 조회 (10개)
        boolean isDeleted = false;
        List<Space> spaces = findSpacesByPaging(isDeleted, 0, 10);

        /**
         * Space 접근 가능 사용자 목록 조회
         */
        List<SpaceMain> spaceMainList = new ArrayList<>();
        for (Space space : spaces) {
            Integer spaceId = space.getSpaceId();
            List<Wiki> wikis = wikiService.findBySpaceId(spaceId);
            List<Keyword> keywords = keywordService.findBySpaceId(spaceId, false);
            SpaceMain spaceMain = new SpaceMain(space, wikis.size(), keywords);
            spaceMainList.add(spaceMain);
        }

        User user = loggedUserManager.getUser();

        if (user == null || user.isGuest()) {
            log.debug("# 로그인 사용자 정보가 존재하지 않습니다.");
            mav.addObject("myFavoriteSpaceList", null);
        } else {
            /**
             * 내 즐겨찾기 공간 정보 조회
             */
            List<Space> myFavoriteSpaceList = spaceService.findUserFavoriteSpace(user.getUserId(), false);
            List<SpaceMain> favoriteSpaces = Lists.newArrayList();


            if (CollectionUtils.isEmpty(myFavoriteSpaceList)) {
                log.debug("## 즐겨찾기 공간이 없습니다.");
                mav.addObject("myFavoriteSpaceList", null);
            } else {
                for (Space space : myFavoriteSpaceList) {
                    Integer spaceId = space.getSpaceId();
                    List<Wiki> wikis = wikiService.findBySpaceId(spaceId);
                    List<Keyword> keywords = keywordService.findBySpaceId(spaceId, false);
                    SpaceMain spaceMain = new SpaceMain(space, wikis.size(), keywords);
                    favoriteSpaces.add(spaceMain);
                }
                mav.addObject("myFavoriteSpaceList", favoriteSpaces);
            }

        }

        /**
         * 최근 수정된 위키 정보 조회 10개
         */
        List<DisplayWiki> updateWikiList = wikiService.findUpdateWikiList(0, 10);

        List<SpaceWikiList> spaceWikiLists = new ArrayList<>();
        for (DisplayWiki displayWiki : updateWikiList) {
            Wiki wiki = displayWiki.getWiki();
            List<WikiReply> replies = wiki.getWikiReplies();
            List<Keyword> keywords = keywordService.findByWikiId(wiki.getWikiId(), false);
            User userInfo = new User();
            userInfo.setUserId(wiki.getUserId());
            userInfo.setUserNick(wiki.getUserNick());
            // 속도상의 이슈로 위키의 리플갯수 조회하지 안흠
            SpaceWikiList spaceWikiList = new SpaceWikiList(wiki, userInfo, keywords, replies.size());
            spaceWikiLists.add(spaceWikiList);
        }


        mav.addObject("readMoreCount", spaces.size());
        mav.addObject("spaceMainList", spaceMainList);
        mav.addObject("spaceWikiLists", spaceWikiLists);

        return mav;
    }

    public List<Space> findSpacesByPaging(boolean isDeleted, Integer startIdx, Integer endIdx) {
        return spaceService.findAllByCondition(isDeleted, startIdx, endIdx);
    }

    @PreAuthorize("hasAuthority('USER')")
    @RequestMapping("/space/form")
    public ModelAndView form(Model model) {
        log.debug("# message : {}", message);

        User user = loggedUserManager.getUser();
        UserFavorite userFavorite = null;

        if (user == null) {
            return sendAccessDenied();
        }


        ModelAndView mav = new ModelAndView("/space/form");
        mav.addObject("message", message);
        mav.addObject("user", user);
        return mav;
    }


    @PreAuthorize("hasAuthority('USER')")
    @RequestMapping(value = "/space/add", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData<Space> saveSpace(@ModelAttribute Space space, @ModelAttribute Keyword keyword) throws IllegalAccessException {
        User user = loggedUserManager.getUser();

        if (user == null) {
            throw new IllegalAccessException("로그인 정보가 필요합니다.");
        }

        // TODO List 차후 로그인으로 변경
        space.setInsertDate(new Date());
        space.setInsertUserId(user.getUserId());
        space.setInsertUserNick(user.getUserNick());
        space.setUpdateDate(new Date());

        Space result = spaceService.saveWithKeyword(space, keyword, ActivityType.CREATE_SPACE);
        log.debug("#result : [{}]", result);
        return ResponseData.createSuccessResult(result);
    }

    /**
     * 수정 폼
     * @param spaceId
     * @return
     */
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @RequestMapping(value = "/space/edit/{spaceId}", method = RequestMethod.GET)
    public ModelAndView editSpace(@PathVariable Integer spaceId) throws IllegalAccessException {
        Space space = spaceService.findOne(spaceId);
        User user = loggedUserManager.getUser();
        if (space.getInsertUserId() != user.getUserId()) {
            throw new IllegalAccessException("공간을 수정할 권한이 없습니다.");
        }
        List<Keyword> keywords = keywordService.findBySpaceId(spaceId, false);
        ModelAndView mav = new ModelAndView("/space/edit");
        mav.addObject("user", user);
        mav.addObject("space", space);
        mav.addObject("keywords", keywords);
        return mav;
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @RequestMapping(value = "/space/update", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData<Space>  updateSpace(@ModelAttribute Space space, @ModelAttribute Keyword keyword) throws IllegalAccessException {
        User user = loggedUserManager.getUser();

        if (user == null) {
            throw new IllegalAccessException("로그인 정보가 필요합니다.");
        }

        Space spaceEntity = spaceService.findOne(space.getSpaceId());

        if (spaceEntity.getInsertUserId() != user.getUserId()) {
            throw new IllegalAccessException("공간을 수정할 권한이 없습니다.");
        }

        bindSpaceEntity(space, user, spaceEntity);

        Space result = spaceService.saveWithKeyword(spaceEntity, keyword, ActivityType.UPDATE_SPACE);
        log.debug("#result : [{}]", result);
        return ResponseData.createSuccessResult(result);
    }

    /**
     * 공간 정보 수정시 원본 데이터를 바인딩 한다.
     * @param space
     * @param user
     * @param spaceEntity
     */
    public void bindSpaceEntity(Space space, User user, Space spaceEntity) {
        spaceEntity.setDescription(space.getDescription());
        spaceEntity.setDescriptionMarkup(space.getDescriptionMarkup());
        spaceEntity.setTitle(space.getTitle());
        if (space.getUploadYn().equals("Y")) {
            spaceEntity.setTitleImagePath(space.getTitleImagePath());
            spaceEntity.setTitleImage(space.getTitleImage());
        }
        spaceEntity.setPrivate(space.isPrivate());
        spaceEntity.setLayoutType(space.getLayoutType());
        spaceEntity.setUpdateDate(new Date());
        spaceEntity.setUpdateUserId(user.getUserId());
        spaceEntity.setUpdateUserNick(user.getUserNick());
    }

    /**
     * 공간 메인 조회
     *
     * @param spaceId
     * @return
     */
    @RequestMapping(value = "/space/{spaceId}", method = RequestMethod.GET)
    public ModelAndView spaceDetail(@PathVariable Integer spaceId) {
        Space space = spaceService.findOne(spaceId);

        User spaceUser = userService.findByUserId(space.getInsertUserId());

        // Space 생성시 선택한 Layout 옵션의 View를 보여준다.
        String view = "/space/" + StringUtil.lowerCase(space.getLayoutType().name());

        log.debug("# view : {}", view);

        ModelAndView mav = null;


        // 최근 수정된 위키 목록
        List<Wiki> updatedWikis = wikiService.findSortAndModifiedBySpaceId(spaceId, 0, 10);
        List<Wiki> spaceWikis = wikiService.findBySpaceId(spaceId);

        // 이 공간의 활동 내역을 조회한다. 저장일 역순
        List<Activity> activities = activityService.findBySpaceId(spaceId);
        List<SpaceActivityList> spaceActivityLists = new ArrayList<>();

        for (Activity activity : activities) {

            User userInfo = userService.findByUserId(activity.getUserId());
            SpaceActivityList spaceActivity = new SpaceActivityList(activity, userInfo);

            spaceActivityLists.add(spaceActivity);
        }

        User user = loggedUserManager.getUser();
        UserFavorite userFavorite = null;

        if (user == null) {
            return sendAccessDenied();
        }

        mav = new ModelAndView(view);


        if (!user.isGuest()) {
            List<UserFavorite> userFavorites = userFavoriteService.findBySpaceIdAndUserIdAndIsDeleted(spaceId, user.getUserId(), false);
            userFavorite = Iterables.getFirst(userFavorites, null);
        }
        log.debug("# spaceWikis : {}", spaceWikis);


        List<Wiki> wikiListInSpace = wikiService.findBySpaceIdAndSort(spaceId);
        List<WikiTree> wikiTrees = bindWikiTree(wikiListInSpace);

        log.info("### wikiTrees : {}", wikiTrees);


        for (int i = 0; i < wikiTrees.size(); i++) {
            if (wikiTrees.get(i).getWikiId() == wikiTrees.get(i).getParentsId()) {
                continue;
            } else {
                // 자식 위키가 있음
                setChild(wikiTrees, wikiTrees.get(i).getParentsId());
            }
        }

        int depth = 0;
        for (int x = 0; x < wikiTrees.size(); x++) {
            if (depth != 0 && depth == wikiTrees.get(x).getDepthIdx()) {
                wikiTrees.get(x-1).setHasBrother(true);
            }

            depth = wikiTrees.get(x).getDepthIdx();
        }



        System.out.println("@@@ wikiList : " + wikiTrees);
        String trees = htmlTree(wikiTrees);
        System.out.println("##### trees : " + trees);

        mav.addObject("trees", trees);
        mav.addObject("spaceWikis", spaceWikis);
        mav.addObject("updatedWikis", updatedWikis);
        mav.addObject("space", space);
        mav.addObject("spaceUser", spaceUser);
        mav.addObject("userFavorite", userFavorite);
        mav.addObject("activities", activities);
        mav.addObject("spaceActivityLists", spaceActivityLists);


        return mav;
    }

    private String htmlTree(List<WikiTree> wikiList) {
        String appendTag = "<ul id=\"tree\">\n";
        int groupId = 0;
        int depth = 0;

        for (int i = 0; i < wikiList.size(); i++) {
            System.out.println(i + "번째  ## group = " + wikiList.get(i).getGroupIdx()
                    + " ## parentId = " + wikiList.get(i).getParentsId()
                    + " ## wikiId = " + wikiList.get(i).getWikiId()
                    + " ## depth = " + wikiList.get(i).getDepthIdx()
                    + " ## child = " + wikiList.get(i).isHasChild()
                    + " ## brother = " + wikiList.get(i).isHasBrother()
                    + " || title = " + wikiList.get(i).getTitle() );
            boolean isClosed = wikiList.get(i).getDepthIdx() < depth;

            if (groupId != wikiList.get(i).getGroupIdx()) { //새로은 그룹 아이디일 경우 새로운 li생성

                if (isClosed) {
                    appendTag += " \t\t</li>\n\t</ul>\n</li>\n";
                }

                appendTag += "\t<li><strong><a href=\"#\">" + wikiList.get(i).getTitle() +"</a></strong>\n";

                if (wikiList.get(i).isHasChild() == true) {
                    appendTag += "\t<ul>\n";
                }
            } else {

                System.out.println("depth = " + depth + " listDepth = " + wikiList.get(i).getDepthIdx() + " isClose =" + isClosed);

                if (isClosed) {
                    appendTag += "\t\t</li>\n\t</ul>\n";
                    appendTag += "\t\t</li>\n";
                }
                appendTag += "\t\t<li><a href=\"#\">" + wikiList.get(i).getTitle() +"</a>";
                if (wikiList.get(i).isHasChild() == true) {
                    appendTag += "\n\t\t<ul>\n";
                } else {
                    if (!wikiList.get(i).isHasBrother()) {
                        appendTag += "</li>\n\t\t</ul>\n";
                    } else {
                        appendTag += "</li>\n";
                    }
                }
            }


            if (wikiList.size() - 1 == i) { // 마지막 태그
                if (!wikiList.get(i).isHasBrother()) {
                    appendTag += "\t</li>\n";
                }
            }
            depth = wikiList.get(i).getDepthIdx();
            groupId = wikiList.get(i).getGroupIdx();

        }
        appendTag += "</ul>";
        return appendTag;
    }


    private void setChild(List<WikiTree> wikiList, int parentId) {
        for (WikiTree tree : wikiList) {
            if (parentId == tree.getWikiId()) {
                tree.setHasChild(true);
            }
        }
    }

    private List<WikiTree> bindWikiTree(List<Wiki> wikiListInSpace) {
        List<WikiTree> wikiTrees = new ArrayList<>();


        for (Wiki wiki : wikiListInSpace) {
            WikiTree wikiTree = new WikiTree();

            wikiTree.setWikiId(wiki.getWikiId());
            wikiTree.setTitle(wiki.getTitle());
            wikiTree.setDepthIdx(wiki.getDepthIdx());
            wikiTree.setGroupIdx(wiki.getGroupIdx());
            wikiTree.setOrderIdx(wiki.getOrderIdx());
            wikiTree.setParentsId(wiki.getParentsId());
            wikiTrees.add(wikiTree);
        }

        return wikiTrees;
    }



    /**
     * 개설된 공간 수 조회
     *
     * @return
     */
    @RequestMapping(value = "/space/count", method = RequestMethod.GET)
    @ResponseBody
    public String spaceCount() {
        List<Space> spaces = spaceService.findAllByCondition(false);

        return spaces.size() + "";
    }


    /**
     * 공간 즐겨찾기 추가
     * 0 사용자 정보 없음
     * -1 트랜잭션 실패
     * 1 성공
     *
     * @param spaceId
     * @return
     */
    @RequestMapping(value = "/space/addFavorite", method = RequestMethod.GET)
    @ResponseBody
    public String addFavorite(@RequestParam Integer spaceId) {

        User user = loggedUserManager.getUser();

        log.debug("### user = {}", user);

        if (user.isGuest()) {
            log.debug("# 로그인 사용자 정보가 존재하지 않습니다.");
            return "0";
        }
        // 즐겨 찾기 추가는 isDeleted를 false 로 넘김
        Integer result = spaceService.addSpaceFavorite(spaceId, user.getUserId(), false);

        return result.toString();
    }

    /**
     * 공간 즐겨 찾기 취소
     *
     * @param spaceId
     * @return
     */
    @RequestMapping(value = "/space/cancelFavorite", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('USER')")
    @ResponseBody
    public String cancelFavorite(@RequestParam Integer spaceId) {

        User user = loggedUserManager.getUser();
        if (user.isGuest()) {
            log.debug("# 로그인 사용자 정보가 존재하지 않습니다.");
            return "0";
        }
        // 즐겨 찾기 추가는 isDeleted를 false 로 넘김
        Integer result = spaceService.cancelSpaceFavorite(spaceId, user.getUserId(), true);

        return result.toString();
    }


    @RequestMapping("/space/list")
    public ModelAndView list(Model model) {
        ModelAndView mav = new ModelAndView("/space/list");

        return mav;
    }

    /*
    @RequestMapping("/space/wikis")
    @ResponseBody
    public Collection wikiLists(@RequestParam Integer spaceId) {
        List<Wiki> wikiList = wikiService.findBySpaceId(spaceId);


        List<WikiTree> defaultData = new ArrayList<>();

        WikiTreeNode wikiTreeNode = new WikiTreeNode();
        wikiTreeNode.setHref("/1");
        wikiTreeNode.setTags(5);
        wikiTreeNode.setText("자식노드1");
        List<WikiTreeNode> nodes = new ArrayList<>();
        nodes.add(wikiTreeNode);

        WikiTree data = new WikiTree();
        data.setText("Parent 1");
        data.setTags(4);
        data.setHref("#parent 1");
        data.setNodes(nodes);

        defaultData.add(data);

        log.info("### defaultData = {}", defaultData);
        return defaultData;

    }
    */
    @RequestMapping(value = "/space/tree", method = RequestMethod.POST)
    @ResponseBody
    public String treeJson(@RequestParam Integer spaceId) throws IOException {
        List<Wiki> wikiListInSpace = wikiService.findBySpaceIdAndSort(spaceId);
        List<WikiTree> wikiList = bindWikiTree(wikiListInSpace);

        for (int i = 0; i < wikiList.size(); i++) {
            if (wikiList.get(i).getWikiId() == wikiList.get(i).getParentsId()) {
                continue;
            } else {
                // 자식 위키가 있음
                setChild(wikiList, wikiList.get(i).getParentsId());
            }
        }
        ObjectMapper om = new ObjectMapper();

        // {"success" : true, "returnUrl" : "..."}
        String jsonString = om.writeValueAsString(wikiList);
        log.info("### json = " + jsonString);
        return jsonString;
    }


    public ModelAndView sendAccessDenied() {
        ModelAndView mav = new ModelAndView("/common/403");
        mav.addObject("msg", "Hi " + ", you do not have permission to access this page!");

        return mav;
    }

}


