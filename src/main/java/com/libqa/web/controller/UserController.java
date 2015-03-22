package com.libqa.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by yion on 2015. 3. 23..
 */
@Slf4j
@Controller
public class UserController {


    @RequestMapping("/login")
    public ModelAndView fileUpload(Model model) {
        ModelAndView mav = new ModelAndView("/user/login");
        return mav;
    }
}
