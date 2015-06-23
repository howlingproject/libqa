package com.libqa.web.controller;

import com.libqa.application.framework.ResponseData;
import com.libqa.application.util.StringUtil;
import com.libqa.web.domain.Keyword;
import com.libqa.web.domain.Space;
import com.libqa.web.domain.SpaceAccessUser;
import com.libqa.web.domain.Wiki;
import com.libqa.web.service.KeywordService;
import com.libqa.web.service.SpaceService;
import com.libqa.web.service.WikiService;
import com.libqa.web.view.SpaceMain;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

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
    public ModelAndView spaceMain(Model model) {
        log.info("## /main");

        Integer userId = 1;
        /**
         * 전체 space 목록 조회
         */
        boolean isDeleted = false;
        List<Space> spaces = spaceService.findAllByCondition(isDeleted);

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

        /**
         * 내 즐겨찾기 공간 정보 조회
         */
        List<Space> myFavoriteSpaceList = spaceService.findUserFavoriteSpace(userId);

        /**
         * 최근 수정된 위키 정보 조회 10개
         */
        List<Wiki> updateWikiList = wikiService.findByAllWiki(0, 10);


        ModelAndView mav = new ModelAndView("/space/main");
        mav.addObject("readMoreCount", spaces.size());
        mav.addObject("spaceMainList", spaceMainList);
        mav.addObject("myFavoriteSpaceList", myFavoriteSpaceList);
        mav.addObject("updateWikiList", updateWikiList);

        return mav;
    }

    @RequestMapping("/space/form")
    public ModelAndView form(Model model) {
        log.info("# message : {}", message);

        ModelAndView mav = new ModelAndView("/space/form");
        mav.addObject("message", message);
        return mav;
    }


    @RequestMapping(value = "/space/add", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData<Space> saveSpace(@ModelAttribute Space space) {
        // TODO List 차후 로그인으로 변경
        space.setInsertDate(new Date());
        space.setInsertUserId(1);

        Space result = spaceService.saveWithKeyword(space);
        log.debug("#result : [{}]", result);
        return ResponseData.createSuccessResult(result);
    }

    /**
     * 공간 메인 조회
     * @param spaceId
     * @return
     */
    @RequestMapping(value = "/space/{spaceId}", method = RequestMethod.GET)
    public ModelAndView spaceDetail(@PathVariable Integer spaceId) {
        Space space = spaceService.findOne(spaceId);

        // 최근 수정된 위키 목록
        List<Wiki> updatedWikis = wikiService.findSortAndModifiedBySpaceId(spaceId, 0, 10);
        List<Wiki> spaceWikis = wikiService.findBySpaceId(spaceId);

        // Space 생성시 선택한 Layout 옵션의 View를 보여준다.
        String view = "/space/" + StringUtil.lowerCase(space.getLayoutType().name());

        log.info("# view : {}", view);
        ModelAndView mav = new ModelAndView(view);

        mav.addObject("spaceWikis", spaceWikis);
        mav.addObject("updatedWikis", updatedWikis);
        mav.addObject("space", space);
        return mav;
    }

    /**
     * 개설된 공간 수 조회
     * @return
     */
    @RequestMapping(value = "/space/count", method = RequestMethod.GET)
    @ResponseBody
    public String spaceCount() {
        List<Space> spaces = spaceService.findAllByCondition(false);

        return spaces.size()+"";
    }
}


