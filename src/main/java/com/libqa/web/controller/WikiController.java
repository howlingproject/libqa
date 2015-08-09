package com.libqa.web.controller;

import com.libqa.application.enums.ListTypeEnum;
import com.libqa.application.framework.ResponseData;
import com.libqa.application.util.StringUtil;
import com.libqa.web.domain.Keyword;
import com.libqa.web.domain.Space;
import com.libqa.web.domain.Wiki;
import com.libqa.web.service.KeywordListService;
import com.libqa.web.service.KeywordService;
import com.libqa.web.service.SpaceService;
import com.libqa.web.service.WikiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
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
    private SpaceService spaceService;

    @Autowired
    KeywordListService keywordListService;

    @Autowired
    KeywordService keywordService;

    @RequestMapping("wiki/main")
    public ModelAndView main(Model model){
        ModelAndView mav = new ModelAndView("wiki/main");

        List<Wiki> allWiki = wikiService.findByAllWiki(0, 5);
        mav.addObject("allWiki", allWiki);

        //유저 하드코딩
        int userId = 1;
        List<Wiki> resecntWiki = wikiService.findByRecentWiki(userId, 0, 5);
        mav.addObject("resecntWiki", resecntWiki);

        List<Wiki> bestWiki = wikiService.findByBestWiki(0, 5);
        mav.addObject("bestWiki", bestWiki);

        return mav;
    }
/*
    @RequestMapping("wiki/list")
    public ModelAndView list(HttpServletRequest request){
        ModelAndView mav = new ModelAndView("wiki/list");
        List<Wiki> allWiki = null;
        String keyword = StringUtil.convertString(request.getParameter("keyword"), "0");
        Integer keywordId = Integer.parseInt(keyword);
        List<Keyword> keywordList = keywordService.findBykeywordId(keywordId, false);
        if( keywordList != null && keywordList.size() > 0 ){

        }

        allWiki = wikiService.findByAllWiki(0, 10);
        mav.addObject("allWiki", allWiki);

        return mav;
    }*/

    @RequestMapping("wiki/write")
    public ModelAndView write(@ModelAttribute Space modelSpace){
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


        //임시 공간영역 하드코딩
        //Space space = new Space();
        //space.setSpaceId(1);
        //space.setTitle("테스트");
        //mav.addObject("space", space);

        return mav;
    }

    @RequestMapping("wiki/update/{wikiId}")
    public ModelAndView update(@ModelAttribute Space modelSpace, @PathVariable Integer wikiId){
        ModelAndView mav = new ModelAndView("wiki/write");
        log.info("# spaceId : {}", modelSpace.getSpaceId());
        /*
        if( modelSpace.getSpaceId() == null ){
            boolean isDeleted = false;    // 삭제 하지 않은 것
            List<Space> spaceList = spaceService.findAllByCondition(isDeleted);
            mav.addObject("spaceList", spaceList);
        }else{
            Space space = spaceService.findOne(modelSpace.getSpaceId());
            mav.addObject("space", space);
        }
        */
        //임시 공간영역 하드코딩
        Space space = new Space();
        space.setSpaceId(1);
        space.setTitle("테스트");
        mav.addObject("space", space);

        //유저 하드코딩
        int userId = 1;
        Wiki wiki = wikiService.findById(wikiId);
        mav.addObject("wiki", wiki);

        List<Keyword> keywordList = keywordService.findByWikiId(wikiId, false);
        mav.addObject("keywordList", keywordList);

        return mav;
    }

    @RequestMapping(value = "/wiki/{wikiId}", method = RequestMethod.GET)
    public ModelAndView spaceDetail(@PathVariable Integer wikiId) {
        Wiki wiki = wikiService.findById(wikiId);

        log.info("# view : {}", wiki);
        ModelAndView mav = new ModelAndView("wiki/view");

        mav.addObject("wiki", wiki);
        return mav;
    }

    @RequestMapping(value = "wiki/save", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData<?> save(@ModelAttribute Wiki wiki){
        try{
            log.info("####### WIKI SAVE Begin INFO ########");
            log.info("wiki = {}", wiki);
            log.info("wiki.wikiFile = {}", wiki.getWikiFiles());
            wiki.setPasswd("1234");
            wiki.setUserNick("하이");
            wiki.setUserId(1);
            wiki.setInsertDate(new Date());

            Wiki result = wikiService.saveWithKeyword(wiki);

            log.info("####### WIKI SAVE After INFO ########");
            log.info("result = {}", result);

            return ResponseData.createSuccessResult(result);
        }catch(Exception e){
            e.printStackTrace();
            log.error(e.toString());
            return ResponseData.createFailResult(wiki);
        }

    }

    @RequestMapping("wiki/bestList")
    @Deprecated
    public ModelAndView bestList(Model model){
        ModelAndView mav = new ModelAndView("wiki/template/_bestList");

        List<Wiki> list = new ArrayList<Wiki>();
        for(int i=0; i<5; i++){
            Wiki wiki = new Wiki();
            wiki.setUserNick("테스트"+i);
            wiki.setInsertDate(new Date());
            wiki.setLikeCount(i);
            wiki.setContents("지금은 "+i+" 베스트위키 테스트중");
            list.add(wiki);
        }
        mav.addObject("bestList",list);

        return mav;
    }

    @RequestMapping("wiki/allList")
    @Deprecated
    public ModelAndView allList(Model model){
        ModelAndView mav = new ModelAndView("wiki/template/_allList");

        List<Wiki> list = new ArrayList<Wiki>();
        for(int i=0; i<5; i++){
            Wiki wiki = new Wiki();
            wiki.setUserNick("테스트"+i);
            wiki.setInsertDate(new Date());
            wiki.setLikeCount(i);
            wiki.setContents("지금은 "+i+" 모든위키 테스트중");
            list.add(wiki);
        }
        mav.addObject("allList",list);

        return mav;
    }

    @RequestMapping("wiki/recentList")
    @Deprecated
    public ModelAndView recentList(Model model){
        ModelAndView mav = new ModelAndView("wiki/template/_recentList");

        List<Wiki> list = new ArrayList<Wiki>();
        for(int i=0; i<5; i++){
            Wiki wiki = new Wiki();
            wiki.setUserNick("테스트"+i);
            wiki.setTitle("위키 타이틀");
            wiki.setInsertDate(new Date());
            wiki.setLikeCount(i);
            wiki.setContents("지금은 "+i+" 최근활동위키 테스트중");
            list.add(wiki);
        }
        mav.addObject("recentList",list);


        return mav;
    }

    @RequestMapping(value = "wiki/count", method = RequestMethod.GET)
    @ResponseBody
    public String wikiCount() {
        List<Wiki> wikis = wikiService.findAllByCondition();

        return wikis.size() + "";
    }

    @RequestMapping(value = "wiki/list/{listType}", method = RequestMethod.GET)
    public ModelAndView wikiList(@PathVariable("listType") String listType) {
        ModelAndView mav = new ModelAndView("wiki/list");

        listType = StringUtil.nullToString(listType);

        if( ListTypeEnum.ALL.getName().equals(listType) ){
            List<Wiki> allWiki = wikiService.findByAllWiki(0, 15);
            mav.addObject("listWiki", allWiki);
            mav.addObject("listTitle","전체 위키 List");

        }else if( ListTypeEnum.BEST.getName().equals(listType)){
            List<Wiki> bestWiki = wikiService.findByBestWiki(0, 15);
            mav.addObject("listWiki", bestWiki);
            mav.addObject("listTitle","베스트 위키 List");

        }else if( ListTypeEnum.RESENT.getName().equals(listType) ){
            //유저 하드코딩
            int userId = 1;
            List<Wiki> resecntWiki = wikiService.findByRecentWiki(userId, 0, 15);
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


}
