package com.libqa.web.controller;

import com.libqa.application.util.LoggedUserManager;
import com.libqa.web.domain.FeedThread;
import com.libqa.web.domain.User;
import com.libqa.web.service.feed.FeedThreadService;
import com.libqa.web.service.qa.QaService;
import com.libqa.web.view.feed.DisplayFeedBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * @Author : yion
 * @Date : 2015. 12. 18.
 * @Description : MyList 메뉴 화면 컨트롤러
 */
@Slf4j
@Controller
public class MyListController {

    @Autowired
    private LoggedUserManager loggedUserManager;
    @Autowired
    private QaService qaService;
    @Autowired
    private FeedThreadService feedThreadService;
    @Autowired
    private DisplayFeedBuilder displayFeedBuilder;

    @RequestMapping("/my/feed")
    public ModelAndView myFeed(ModelAndView mav) {
        User viewer = loggedUserManager.getUser();
        List<FeedThread> feedThreads = feedThreadService.searchRecentlyFeedThreadsByUser(viewer);

        mav.addObject("data", displayFeedBuilder.build(feedThreads, viewer));
        mav.setViewName("/my/feedList");
        return mav;
    }

    @RequestMapping("/my/qa")
    public ModelAndView myQa(Model model) {
        Integer qaTotalCount = qaService.getQaTotalCount();
        Integer qaNotReplyedCount = qaService.getQaNotReplyedCount();
        ModelAndView mav = new ModelAndView("/my/qaList");
        mav.addObject("qaTotalCount", qaTotalCount);
        mav.addObject("qaNotReplyedCount", qaNotReplyedCount);
        return mav;
    }

    @RequestMapping("/my/wiki")
    public ModelAndView myWiki(Model model) {
        ModelAndView mav = new ModelAndView("/my/wikiList");
        return mav;
    }

    @RequestMapping("/my/favor")
    public ModelAndView myFavor(Model model) {
        ModelAndView mav = new ModelAndView("/my/favorList");
        return mav;
    }


}
