package com.libqa.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.libqa.application.enums.ActivityType;
import com.libqa.application.enums.Role;
import com.libqa.application.enums.StatusCode;
import com.libqa.application.framework.ResponseData;
import com.libqa.application.util.LibqaConstant;
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
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
        boolean morePage = false;   // 더보기 여부
        SpaceMainList spacePages = spaceService.findPageBySort(isDeleted, LibqaConstant.PAGE_START_INDEX,
                LibqaConstant.SPACE_PAGE_SIZE,
                LibqaConstant.SORT_TYPE_TITLE);
        Long totalCount = spacePages.getTotalElements();
        Integer currentPage = spacePages.getCurrentPage();
        Integer totalPage = spacePages.getTotalPages();

        User user = loggedUserManager.getUser();

        if (user == null || user.isGuest()) {
            log.debug("# 로그인 사용자 정보가 존재하지 않음.");
            mav.addObject("myFavoriteSpaceList", null);
        } else {
            /**
             * 내 즐겨찾기 공간 정보 조회
             */
            List<Space> myFavoriteSpaceList = spaceService.findUserFavoriteSpace(user.getUserId(), false);
            List<SpaceMain> favoriteSpaces;

            if (CollectionUtils.isEmpty(myFavoriteSpaceList)) {
                log.debug("## 즐겨찾기 공간이 없음.");
                mav.addObject("myFavoriteSpaceList", null);
            } else {
                favoriteSpaces = spaceService.convertSpaceMain(myFavoriteSpaceList);
                mav.addObject("myFavoriteSpaceList", favoriteSpaces);
            }

        }

        /**
         * 최근 수정된 위키 정보 조회 10개 페이징 처리
         */
        SpaceWikiList spaceWikiPages = spaceService.findWikiPageBySort(isDeleted, LibqaConstant.PAGE_START_INDEX,
                LibqaConstant.SPACE_WIKI_SIZE,
                LibqaConstant.SORT_TYPE_DATE);

        if (totalCount > LibqaConstant.SPACE_PAGE_SIZE) {
            morePage = true;
        }

        log.info("### spacePages = {}", spacePages.getCurrentPage());
        log.info("### spacePages = {}", spacePages.getTotalElements());
        log.info("### spacePages = {}", spacePages.getTotalPages());

        mav.addObject("morePage", morePage);
        mav.addObject("totalCount", totalCount);
        mav.addObject("currentPage", currentPage);
        mav.addObject("totalPage", totalPage);
        mav.addObject("spaceMainList", spacePages.getSpaceMainList());
        mav.addObject("spacePageSize", LibqaConstant.SPACE_PAGE_SIZE);
        mav.addObject("spaceWikiSize", LibqaConstant.SPACE_WIKI_SIZE);
        mav.addObject("wikiTotalCount", spaceWikiPages.getTotalElements());
        mav.addObject("wikiCurrentPage", spaceWikiPages.getCurrentPage());
        mav.addObject("wikiTotalPage", spaceWikiPages.getTotalPages());
        mav.addObject("spaceWikiList", spaceWikiPages.getSpaceWikiList());

        return mav;
    }

    /**
     * 공간 더보기 구현
     *
     * @param sortType
     * @param startNum
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/space/more", method = RequestMethod.GET)
    @ResponseBody
    public ResponseData<SpaceMainList> findMoreSpaceList(@RequestParam String sortType,
                                                         @RequestParam Integer startNum,
                                                         @RequestParam Integer pageSize) {
        SpaceMainList spaceMainList = spaceService.findPageBySort(false, startNum, pageSize, sortType);

        log.info("### spaceMainList = {}", spaceMainList.getCurrentPage());
        log.info("### spaceMainList = {}", spaceMainList.getTotalElements());
        log.info("### spaceMainList = {}", spaceMainList.getTotalPages());

        return ResponseData.createSuccessResult(spaceMainList);
    }

    /**
     * @param sortType
     * @param startNum
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/space/morewiki", method = RequestMethod.GET)
    @ResponseBody
    public ResponseData<SpaceWikiList> findMoreWikis(@RequestParam String sortType,
                                                     @RequestParam Integer startNum,
                                                     @RequestParam Integer pageSize) {
        SpaceWikiList spaceWikiList = spaceService.findWikiPageBySort(Boolean.FALSE, startNum, pageSize, sortType);

        return ResponseData.createSuccessResult(spaceWikiList);
    }

    /**
     * 이름순, 최신순 정렬
     *
     * @param sortType
     * @return
     */
    @RequestMapping(value = "/space/render", method = RequestMethod.GET)
    @ResponseBody
    public ResponseData<SpaceMainList> renderSpace(@RequestParam String sortType) {
        log.info("## sortType = {}", sortType);

        SpaceMainList spaceMainList = spaceService.findPageBySort(false, 0, LibqaConstant.SPACE_PAGE_SIZE, sortType);
        return ResponseData.createSuccessResult(spaceMainList);

    }

    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
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


    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
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
     *
     * @param spaceId
     * @return
     */
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    @RequestMapping(value = "/space/edit/{spaceId}", method = RequestMethod.GET)
    public ModelAndView editSpace(@PathVariable Integer spaceId) throws IllegalAccessException {
        Space space = spaceService.findOne(spaceId);
        User user = loggedUserManager.getUser();

        // 수정할 권한이 있는지 체크 한다.
        checkAuthorize(space, user);

        List<Keyword> keywords = keywordService.findBySpaceId(spaceId, false);
        ModelAndView mav = new ModelAndView("/space/edit");
        mav.addObject("user", user);
        mav.addObject("space", space);
        mav.addObject("keywords", keywords);
        return mav;
    }

    /**
     * 수정 접근 권한 체크, 로그인이 없을 경우, ADMIN이 아닌 사용자의 로그인 아이디와 입력자의 아이디가 다를 경우
     *
     * @param space
     * @param user
     * @throws IllegalAccessException
     */
    public void checkAuthorize(Space space, User user) throws IllegalAccessException {
        if (user == null) {
            throw new IllegalAccessException("로그인 정보가 필요합니다.");
        }

        if (!user.getRole().equals(Role.ADMIN)) {
            if (space.getInsertUserId() != user.getUserId()) {
                throw new IllegalAccessException("공간을 수정할 권한이 없습니다.");
            }
        }
    }

    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    @RequestMapping(value = "/space/update", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData<Space> updateSpace(@ModelAttribute Space space, @ModelAttribute Keyword keyword) throws IllegalAccessException {
        User user = loggedUserManager.getUser();
        Space spaceEntity = spaceService.findOne(space.getSpaceId());

        // 수정할 권한이 있는지 체크 한다.
        checkAuthorize(space, user);

        bindSpaceEntity(space, user, spaceEntity);

        Space result = spaceService.saveWithKeyword(spaceEntity, keyword, ActivityType.UPDATE_SPACE);
        log.debug("#result : [{}]", result);
        return ResponseData.createSuccessResult(result);
    }


    /**
     * 공간의 삭제는 관리자 이거나, 본인 일 경우 가능하지만 본인이라고 하더라도 하위 위키가 없을때만 삭제가 가능하다.
     *
     * @param spaceId
     * @return
     * @throws IllegalAccessException
     */
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    @RequestMapping(value = "/space/delete", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData<Space> deleteSpace(@RequestParam Integer spaceId) throws IllegalAccessException {
        User user = loggedUserManager.getUser();
        Space result = null;

        boolean canDelete = false;      // 삭제 가능 여부
        boolean existWiki = false;      // 하위 위키 존재 여부
        Space spaceEntity = spaceService.findOne(spaceId);

        if (user.getRole().equals(Role.ADMIN)) {
            canDelete = true;
        } else {
            if (spaceEntity.getInsertUserId() == user.getUserId()) {

                List<Wiki> wikis = new ArrayList<>();
                wikis = wikiService.findBySpaceId(spaceId);

                if (CollectionUtils.isEmpty(wikis)) {
                    canDelete = true;
                } else {
                    canDelete = false;
                    existWiki = true;
                }
            }
        }


        log.info("#### canDelete = {} ", canDelete);
        log.info("#### existWiki = {} ", existWiki);
        try {
            if (canDelete) {
                result = spaceService.delete(spaceEntity, user);
                return ResponseData.createResult(StatusCode.SUCCESS.getCode(), "정상적으로 처리 되었습니다.", result);
            } else {
                return ResponseData.createResult(StatusCode.NONE.getCode(), "위키가 존재하므로 공간을 삭제할 수 없습니다. <BR>관리자에게 문의하세요.", null);
            }
        } catch (Exception e) {
            log.error("공간 삭제 에러 = {}", e.getMessage());
            return ResponseData.createFailResult();

        }
    }

    /**
     * 공간 정보 수정시 원본 데이터를 바인딩 한다.
     *
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

        // 이 공간의 활동 내역을 조회한다. 저장일 역순
        List<Activity> activities = activityService.findBySpaceId(spaceId);
        List<SpaceActivityList> spaceActivityLists = new ArrayList<>();

        for (Activity activity : activities) {
            User userInfo = userService.findByUserId(activity.getUserId());
            SpaceActivityList spaceActivity = new SpaceActivityList(activity, userInfo);

            spaceActivityLists.add(spaceActivity);
        }

        // 최근 수정된 위키 목록
        List<Wiki> updatedWikis = wikiService.findSortAndModifiedBySpaceId(spaceId, 0, LibqaConstant.SPACE_WIKI_SIZE);
        List<SpaceWiki> spaceWikis = new ArrayList<>();
        for (Wiki wiki : updatedWikis) {
            User user = userService.findByUserId(wiki.getUserId());
            SpaceWiki spaceWiki = new SpaceWiki();

            spaceWiki.setUser(user);
            spaceWiki.setWiki(wiki);
            spaceWiki.setReplyCount(wiki.getReplyCount());
            spaceWikis.add(spaceWiki);
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

        boolean canDeleted = canDeleted(space, user);

        mav.addObject("spaceWikis", spaceWikis);
        mav.addObject("space", space);
        mav.addObject("canDeleted", canDeleted);
        mav.addObject("spaceUser", spaceUser);
        mav.addObject("userFavorite", userFavorite);
        mav.addObject("activities", activities);
        mav.addObject("spaceActivityLists", spaceActivityLists);

        return mav;
    }

    /**
     * 공간 삭제 버튼이 보이는지 안보이는지 여부를 결정한다.
     * 하위에 위키가 있을 경우 삭제 버튼은 보이지만 삭제를 할 수는 없다. (경고메시지 출력)
     *
     * @param space
     * @param user
     * @return
     */
    public boolean canDeleted(Space space, User user) {
        boolean canDeleted = false;

        // admin user 일 경우
        if (user.isAdmin()) {
            canDeleted = true;
            // 공간의 생성자와 현재 로그인 사용자의 아이디가 같을 경우
        } else if (space.getInsertUserId().equals(user.getUserId())) {
            canDeleted = true;
        }
        return canDeleted;
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
    @PreAuthorize("hasAuthority('USER')")
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
    @PreAuthorize("hasAuthority('USER')")
    @RequestMapping(value = "/space/cancelFavorite", method = RequestMethod.GET)
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

    @RequestMapping(value = "/space/tree", method = RequestMethod.POST, produces = "text/html; charset=utf8")
    @ResponseBody
    public String treeJson(@RequestParam Integer spaceId) throws IOException {
        List<Wiki> wikiListInSpace = wikiService.findBySpaceIdAndSort(spaceId);
        List<TreeModel> wikiList = bindTree(wikiListInSpace);

        for (int i = 0; i < wikiList.size(); i++) {
            if (wikiList.get(i).getId() == wikiList.get(i).getParentId()) {
                continue;
            } else {
                // 자식 위키가 있음
                setChild(wikiList, wikiList.get(i).getParentId());
            }
        }

        ObjectMapper om = new ObjectMapper();

        // {"success" : true, "returnUrl" : "..."}
        String jsonString = om.writeValueAsString(wikiList);
        log.info("### json = " + jsonString);
        return jsonString;
    }

    private List<TreeModel> bindTree(List<Wiki> wikiListInSpace) {
        List<TreeModel> models = new ArrayList<>();
        for (Wiki wiki : wikiListInSpace) {
            TreeModel model = new TreeModel();

            log.info("### wiki id = " + wiki.getWikiId());
            log.info("### wiki parent = " + wiki.getParentsId());
            log.info("### wiki result = " + wiki.getWikiId().equals(wiki.getParentsId()));

            if (wiki.getWikiId().equals(wiki.getParentsId())) {
                model.setParentId(0);
            } else {
                model.setParentId(wiki.getParentsId());
            }
            model.setId(wiki.getWikiId());
            model.setText(wiki.getTitle());
            String[] counts = new String[1];
            counts[0] = wiki.getReplyCount() + "";
            model.setTags(counts);
            //model.setNodes(null);
            model.setHref("/wiki/" + wiki.getWikiId());

            models.add(model);

        }

        return models;
    }

    private void setChild(List<TreeModel> wikiList, int parentId) {
        for (TreeModel tree : wikiList) {
            if (parentId == tree.getId()) {
                tree.setHasChild(true);
            }
        }
    }

    public ModelAndView sendAccessDenied() {
        ModelAndView mav = new ModelAndView("/common/403");
        mav.addObject("msg", "Hi " + ", you do not have permission to access this page!");

        return mav;
    }


    @RequestMapping(value = "/space/spaces", method = RequestMethod.GET)
    public ModelAndView spaces(HttpRequest request) {
        ModelAndView mav = new ModelAndView("/space/spaces");
        boolean isDeleted = false;
        List<Space> spaceList = spaceService.findAllByCondition(isDeleted);

        List<SpaceMain> spaceMains;

        if (CollectionUtils.isEmpty(spaceList)) {

            mav.addObject("spaceMainList", null);
        } else {
            spaceMains = spaceService.convertSpaceMain(spaceList);
            mav.addObject("spaceMainList", spaceMains);
        }


        // TODO 접근 권한 없는 스페이스 목록은 보이지 않아야 한다.
        User user = loggedUserManager.getUser();

        return mav;
    }
}



