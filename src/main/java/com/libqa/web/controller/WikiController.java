package com.libqa.web.controller;

import com.google.common.base.MoreObjects;
import com.google.common.primitives.Ints;
import com.libqa.application.enums.ListType;
import com.libqa.application.enums.WikiLikesType;
import com.libqa.application.enums.WikiRevisionActionType;
import com.libqa.application.framework.ResponseData;
import com.libqa.application.util.LoggedUser;
import com.libqa.application.util.StringUtil;
import com.libqa.web.domain.*;
import com.libqa.web.service.common.ActivityService;
import com.libqa.web.service.common.KeywordListService;
import com.libqa.web.service.common.KeywordService;
import com.libqa.web.service.space.SpaceService;
import com.libqa.web.service.wiki.WikiReplyService;
import com.libqa.web.service.wiki.WikiService;
import com.libqa.web.view.DisplayWiki;
import com.libqa.web.view.DisplayWikiLike;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by songanji on 2015. 3. 1..
 */
@RestController
@RequestMapping("/")
@Slf4j
public class WikiController {

    @Autowired
    WikiService wikiService;

    @Autowired
    WikiReplyService wikiReplyService;

    @Autowired
    private SpaceService spaceService;

    @Autowired
    KeywordListService keywordListService;

    @Autowired
    KeywordService keywordService;

    @Autowired
    private ActivityService activityService;

    @Autowired
    private LoggedUser loggedUser;

    @RequestMapping("wiki/main")
    public ModelAndView main(Model model){
        ModelAndView mav = new ModelAndView("wiki/main");

        List<DisplayWiki> allWiki = wikiService.findByAllWiki(0, 10);
        mav.addObject("allWiki", allWiki);

        User user = loggedUser.get();
        if(isUser(user)){
            Integer userId = user.getUserId();
            List<DisplayWiki> resecntWiki = wikiService.findByRecentWiki(userId, 0, 4);
            mav.addObject("resecntWiki", resecntWiki);
        }
        List<DisplayWiki> bestWiki = wikiService.findByBestWiki(0, 4);
        mav.addObject("bestWiki", bestWiki);

        return mav;
    }

    private boolean isUser(User user) {
        return (user != null && user.getUserId() != null );
    }

    @PreAuthorize("hasAuthority('USER')")
    @RequestMapping("wiki/write")
    public ModelAndView write(@ModelAttribute Space modelSpace){
        ModelAndView mav = wikiWrite(modelSpace, null);
        return mav;
    }

    @RequestMapping("wiki/write/{wikiId}" )
    public ModelAndView writeSub(@ModelAttribute Space modelSpace, @PathVariable("wikiId") Integer wikiId){
        ModelAndView mav = wikiWrite(modelSpace,wikiId);
        return mav;
    }

    private ModelAndView wikiWrite(Space modelSpace, Integer wikiId){
        ModelAndView mav = new ModelAndView("wiki/write");
        log.info("# spaceId : {}", modelSpace.getSpaceId());

        if( modelSpace.getSpaceId() == null ){
            boolean isDeleted = false;    // 삭제 하지 않은 것
            List<Space> spaceList = spaceService.findAllByCondition(isDeleted);
            mav.addObject("spaceList", spaceList);
        }else{
            Space space = spaceService.findOne(modelSpace.getSpaceId());
            mav.addObject("space", space);
        }
        mav.addObject("parentsId",wikiId);

        return mav;
    }

    @RequestMapping(value = "/wiki/{wikiId}", method = RequestMethod.GET)
    public ModelAndView wikiDetail(@PathVariable Integer wikiId) {
        ModelAndView mav = new ModelAndView("wiki/view");

        Wiki wiki = wikiService.findById(wikiId);
        //List<WikiReply> wikiReply = wiki.getWikiReplies();
        log.info("# view : {}", wiki);
        Wiki parentWiki = wikiService.findByParentId(wiki.getParentsId());
        List<Wiki> subWikiList = wikiService.findBySubWikiId(wiki.getWikiId());

        //List<Activity> activityList = activityService.findByWikiId(wikiId);

        mav.addObject("wiki", wiki);
        mav.addObject("subWikiList", subWikiList);
        mav.addObject("parentWiki", parentWiki);
        //mav.addObject("activityList", activityList);

        return mav;
    }

    @RequestMapping("wiki/update/{wikiId}")
    public ModelAndView update(@PathVariable Integer wikiId){
        ModelAndView mav = new ModelAndView("wiki/write");
        log.info("# wikiId : {}", wikiId);

        Wiki wiki = wikiService.findById(wikiId);
        mav.addObject("wiki", wiki);

        Space space = spaceService.findOne(wiki.getSpaceId());
        mav.addObject("space", space);

        List<Keyword> keywordList = keywordService.findByWikiId(wikiId, false);
        mav.addObject("keywordList", keywordList);

        return mav;
    }

    @RequestMapping(value = "/wiki/delete/{wikiId}", method = RequestMethod.GET)
    public ModelAndView wikiDelete(@PathVariable Integer wikiId) {
        log.info("# wikiId : {}", wikiId);

        Wiki wiki = wikiService.findById(wikiId);
        User user = loggedUser.get();
        int userId = user.getUserId();
        //위키만든 유저만 삭제가능
        if( wiki.getUserId() == userId ){
            wiki.setDeleted(true);
            wikiService.save(wiki);
        }
        RedirectView rv = new RedirectView("/wiki/main");
        rv.setExposeModelAttributes(false);
        return new ModelAndView(rv);
    }

    @RequestMapping(value = "/wiki/lock/{wikiId}", method = RequestMethod.GET)
    public ModelAndView wikiLock(@PathVariable Integer wikiId) {
        log.info("# wikiId : {}", wikiId);

        User user = loggedUser.get();
        int userId = user.getUserId();

        Wiki wiki = wikiService.findById(wikiId);

        //위키만든 유저만 삭제가능
        if( wiki.getUserId() == userId ){
            wiki.setLock(true);
            wikiService.save(wiki);
        }
        RedirectView rv = new RedirectView("/wiki/"+wikiId);
        rv.setExposeModelAttributes(false);
        return new ModelAndView(rv);
    }

    @RequestMapping(value = "wiki/save", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData<?> save(@ModelAttribute Wiki wiki, @ModelAttribute Keyword keyword){
        try{
            log.info("####### WIKI SAVE Begin INFO ########");
            log.info("wiki = {}", wiki);
            log.info("wiki.wikiFile = {}", wiki.getWikiFiles());
            User user = loggedUser.get();
            Date now = new Date();
            wiki.setUserNick(user.getUserNick());
            wiki.setUserId(user.getUserId());

            wiki.setInsertDate(now);
            wiki.setUpdateDate(now);

            if( wiki.getParentsId() == null  ){
                wiki.setDepthIdx(1);
            }

            Wiki result = wikiService.saveWithKeyword(wiki, keyword);


            log.info("####### WIKI SAVE After INFO ########");
            log.info("result = {}", result);

            return ResponseData.createSuccessResult(result);
        }catch(Exception e){
            e.printStackTrace();
            log.error(e.toString());
            return ResponseData.createFailResult(wiki);
        }

    }

    @RequestMapping(value = "wiki/update", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData<?> update(@ModelAttribute Wiki paramWiki, @ModelAttribute Keyword paramKeyword){
        try{
            log.info("####### WIKI SAVE Begin INFO ########");
            log.info("wiki = {}", paramWiki);
            log.info("wiki.wikiFile = {}", paramWiki.getWikiFiles());

            User user = loggedUser.get();
            Integer wikiId = paramWiki.getWikiId();

            Wiki currentWiki = wikiService.findById(wikiId);
//            currentWiki.setKeywords(paramKeyword.getKeywords());
            currentWiki.setWikiFiles(paramWiki.getWikiFiles());
            currentWiki.setTitle(paramWiki.getTitle());
            currentWiki.setContents(paramWiki.getContents());
            currentWiki.setContentsMarkup(paramWiki.getContentsMarkup());

            currentWiki.setUserNick(user.getUserNick());
            currentWiki.setUserId(user.getUserId());
            currentWiki.setUpdateDate(new Date());

            Wiki result = wikiService.updateWithKeyword(currentWiki, paramKeyword, WikiRevisionActionType.UPDATE_WIKI);


            log.info("####### WIKI SAVE After INFO ########");
            log.info("result = {}", result);

            return ResponseData.createSuccessResult(result);
        }catch(Exception e){
            e.printStackTrace();
            log.error(e.toString());
            return ResponseData.createFailResult(paramWiki);
        }

    }

    @RequestMapping(value = "wiki/count", method = RequestMethod.GET)
    @ResponseBody
    public String wikiCount() {
        List<Wiki> wikis = wikiService.findAllByCondition();
        return wikis.size() + "";
    }

    @RequestMapping(value = "wiki/list/{listType}", method = RequestMethod.GET)
    public ModelAndView wikiList(@PathVariable("listType") String listType
                                ,@RequestParam("page") Integer page ) {
        ModelAndView mav = new ModelAndView("wiki/list");

        listType = StringUtil.nullToString(listType);
        page = MoreObjects.firstNonNull(page, 0);

        mav.addObject("page",page);
        mav.addObject("pages",(page/6)+1);
        if( ListType.ALL.getName().equals(listType) ){
            List<DisplayWiki> allWiki = wikiService.findByAllWiki(page, 15);

            Long countAllWiki = wikiService.countByAllWiki();
            int AllPage = (int)( countAllWiki / 15 )+1;
            mav.addObject("allPage", (AllPage/6)+1);

            List pageIdx = new ArrayList();
            for( int i=((page/6)+1); i<6; i++ ){
                if( (AllPage/6)+1 >= i ){
                    pageIdx.add(i);
                }
            }
            mav.addObject("pageIdx", pageIdx);

            mav.addObject("listWiki", allWiki);
            mav.addObject("listTitle","전체 위키 List");

        }else if( ListType.BEST.getName().equals(listType)){
            List<DisplayWiki> bestWiki = wikiService.findByBestWiki(0, 15);
            mav.addObject("listWiki", bestWiki);
            mav.addObject("listTitle","베스트 위키 List");

        }else if( ListType.RESENT.getName().equals(listType) ){
            User user = loggedUser.get();
            int userId = 0;
            if(isUser(user)){
                userId = user.getUserId();
            }
            List<DisplayWiki> resecntWiki = wikiService.findByRecentWiki(userId, 0, 15);
            mav.addObject("listWiki", resecntWiki);
            mav.addObject("listTitle","최근 활동 위키 List");
        }else {
            // 에러 처리

        }

        return mav;
    }

    @RequestMapping(value = "wiki/list/keyword/{keywordNm}", method = RequestMethod.GET)
    public ModelAndView keywordWikiList(@PathVariable String keywordNm) {
        ModelAndView mav = new ModelAndView("wiki/list");

        List<Wiki> keyworldsWiki = wikiService.findWikiListByKeyword(keywordNm, 0, 15);
        mav.addObject("listWiki", keyworldsWiki);
        mav.addObject("selectKeywordName", keywordNm);
        mav.addObject("listTitle",keywordNm+" 위키 List");

        return mav;
    }

    @RequestMapping(value = "wiki/search", method = RequestMethod.GET)
    public ModelAndView searchWikiList(@RequestParam String text) {
        ModelAndView mav = new ModelAndView("wiki/list");
        log.info("# searchWikiList : {}", text);

        List<Wiki> keyworldsWiki = wikiService.findWikiListByContentsMarkup(text, 0, 15);
        mav.addObject("listWiki", keyworldsWiki);
        mav.addObject("searchText", text);
        mav.addObject("listTitle",text+" 위키 List");

        return mav;
    }

    @RequestMapping(value = "/wiki/saveLike", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData<DisplayWikiLike> saveLike(
            @RequestParam("type") WikiLikesType type, @RequestParam("num") int num) {
        log.info("# type : {}", type);
        log.info("# num : {}", num);

        DisplayWikiLike displayWikiLike = null;
        try{
            User user = loggedUser.get();
            if(isUser(user)){
                WikiLike wikiLike = new WikiLike();
                wikiLike.setUserId(user.getUserId());
                if( type == WikiLikesType.WIKI ){
                    wikiLike.setWikiId(num);
                }else if( type == WikiLikesType.COMMENT ){
                    wikiLike.setReplyId(num);
                }

                displayWikiLike = wikiService.updateLike(wikiLike);
            }
            return ResponseData.createSuccessResult(displayWikiLike);
        }catch(Exception e){
            return ResponseData.createSuccessResult(displayWikiLike);
        }
    }

    @RequestMapping(value = "wiki/reply/save", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData<?> ReplySave(@ModelAttribute WikiReply wikiReply){
        try{
            log.info("####### WIKI SAVE Begin INFO ########");
            log.info("wiki = {}", wikiReply);
            User user = loggedUser.get();

            wikiReply.setUserNick(user.getUserNick());
            wikiReply.setUserId(user.getUserId());

            wikiReply.setInsertDate(new Date());
            WikiReply result = wikiReplyService.save(wikiReply);

            log.info("####### WIKI SAVE After INFO ########");
            log.info("result = {}", result);

            return ResponseData.createSuccessResult(result);
        }catch(Exception e){
            e.printStackTrace();
            log.error(e.toString());
            return ResponseData.createFailResult(wikiReply);
        }

    }

}
