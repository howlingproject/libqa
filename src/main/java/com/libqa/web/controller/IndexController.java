package com.libqa.web.controller;

import com.libqa.web.service.index.IndexCrawler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Controller
public class IndexController {
    @Autowired
    private IndexCrawler indexCrawler;

    @RequestMapping({"/", "/index"})
    public ModelAndView index(HttpServletRequest request, ModelAndView mav) {
        log.info("request.get = {}", request.getAttribute("isLogin"));
        log.info("request.get = {}", request.getAttribute("userEmail"));
        log.info("request.get = {}", request.getAttribute("userRole"));

        mav.setViewName("index");
        mav.addObject("isLogin", request.getAttribute("isLogin"));
        mav.addObject("displayIndex", indexCrawler.crawl());
        return mav;
    }
}
