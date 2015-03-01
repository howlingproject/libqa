package com.libqa.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
@RequestMapping("/feed")
public class FeedController {
    
    @RequestMapping("/main")
    public ModelAndView main(Model model) {
        ModelAndView mav = new ModelAndView("feed/main");
        return mav;
    }
}
