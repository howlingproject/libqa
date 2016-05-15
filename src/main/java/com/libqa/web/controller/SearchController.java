package com.libqa.web.controller;

import com.libqa.web.service.search.SearchQaService;
import com.libqa.web.service.search.SearchTargetPage;
import com.libqa.web.view.search.DisplaySearchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
public class SearchController {
    @Autowired
    private SearchQaService searchQaService;

    @RequestMapping(value = "/search", method = GET)
    public ModelAndView search(ModelAndView mav, @RequestParam String pageKey, @RequestParam String query) {
        if (SearchTargetPage.isInValidKey(pageKey)) {
            throw new IllegalArgumentException(pageKey + " is invalid pageKey.");
        }

        List<DisplaySearchResult> results = searchQaService.resolve(query);
        mav.addObject("query", query);
        mav.addObject("pageKey", pageKey);
        mav.addObject("totalCount", results.size());
        mav.addObject("results", results);
        mav.setViewName("/search/result");
        return mav;
    }
}
