package com.libqa.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Author : yion
 * @Date : 2015. 12. 18.
 * @Description : MyList 메뉴 화면 컨트롤러
 */
@Slf4j
@Controller
public class MyListController {

    @RequestMapping("/my/feed")
    public ModelAndView myFeed(Model model) {
        ModelAndView mav = new ModelAndView("/my/feedList");
        return mav;
    }

    @RequestMapping("/my/qa")
    public ModelAndView myQa(Model model) {
        ModelAndView mav = new ModelAndView("/my/qaList");
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
