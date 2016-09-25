package com.libqa.web.controller;

import com.libqa.web.service.search.IntegrationSearcher;
import com.libqa.application.enums.SearchTargetPage;
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
    private IntegrationSearcher integrationSearcher;

    @RequestMapping(value = "/search", method = GET)
    public ModelAndView search(@RequestParam String page, @RequestParam String query, ModelAndView mav) {
        SearchTargetPage searchTargetPage = SearchTargetPage.get(page);
        if (searchTargetPage.isInValidPage()) {
            throw new IllegalArgumentException(page+ " is invalid page.");
        }

        List<DisplaySearchResult> results = integrationSearcher.search(searchTargetPage, query);
        mav.addObject("query", query);
        mav.addObject("page", page);
        mav.addObject("pageDesc", searchTargetPage.getDesc());
        mav.addObject("totalCount", results.size());
        mav.addObject("results", results);
        mav.setViewName("/search/result");
        return mav;
    }
}
