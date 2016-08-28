package com.libqa.web.controller;

import com.libqa.web.service.index.IndexCrawler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
public class IndexController {
    @Autowired
    private IndexCrawler indexCrawler;

    @RequestMapping({"", "/index"})
    public ModelAndView index(ModelAndView mav) {
        mav.addObject("displayIndex", indexCrawler.crawl());
        mav.setViewName("index");
        return mav;
    }

    @RequestMapping({"/index2"})
    public ModelAndView index2(ModelAndView mav) {
        mav.setViewName("index2");
        return mav;
    }

    @RequestMapping({"/index3"})
    public ModelAndView index3(ModelAndView mav) {
        mav.setViewName("index3");
        return mav;
    }
    @RequestMapping({"/index4"})
    public ModelAndView index4(ModelAndView mav) {
        mav.setViewName("index4");
        return mav;
    }
}
