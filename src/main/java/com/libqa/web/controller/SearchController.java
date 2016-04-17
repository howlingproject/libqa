package com.libqa.web.controller;

import com.libqa.web.service.search.SearchPage;
import com.libqa.web.service.search.SearchResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
public class SearchController {
    @Autowired
    private SearchResolver searchResolver;

    @RequestMapping(value = "/search", method = GET)
    public ModelAndView search(ModelAndView mav, @RequestParam String page, @RequestParam String query) {
        mav.addObject("query", query);
        mav.addObject("page", SearchPage.findPageName(page));
        mav.addObject("results", searchResolver.resolve());
        mav.setViewName("/search/result");
        return mav;
    }
}
