package com.libqa.web.controller;

import com.libqa.application.util.LoggedUserManager;
import com.libqa.web.domain.FeedThread;
import com.libqa.web.domain.User;
import com.libqa.web.service.feed.FeedThreadService;
import com.libqa.web.view.feed.DisplayFeedBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
public class FeedPageController {
    @Autowired
    private LoggedUserManager loggedUserManager;
    @Autowired
    private FeedThreadService feedThreadService;
    @Autowired
    private DisplayFeedBuilder displayFeedBuilder;

    @RequestMapping(value = "/feed", method = GET)
    public ModelAndView main(ModelAndView mav) {
        User viewer = loggedUserManager.getUser();
        List<FeedThread> feedThreads = feedThreadService.getRecentlyFeedThreads();

        mav.addObject("loggedUser", viewer);
        mav.addObject("data", displayFeedBuilder.build(feedThreads, viewer));
        mav.setViewName("feed/main");
        return mav;
    }

    @RequestMapping(value = "/feed/{feedThreadId}", method = GET)
    public ModelAndView view(@PathVariable Integer feedThreadId, ModelAndView mav) {
        User viewer = loggedUserManager.getUser();
        FeedThread feedThread = feedThreadService.getByFeedThreadId(feedThreadId);

        mav.addObject("displayFeed", displayFeedBuilder.build(feedThread, viewer));
        mav.setViewName("feed/view");
        return mav;
    }
}
