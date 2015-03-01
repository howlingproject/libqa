package com.libqa.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.libqa.application.enums.SpaceViewEnum;

/**
 * Created by yion on 2015. 2. 8..
 */
@Controller
@Slf4j
public class SpaceController {

    @Value("${howling.hello.message}")
    private String message;

    @RequestMapping("/space/form")
    public ModelAndView index(Model model) {
        log.debug("##############################");
        log.info("# message : {}", message);

        ModelAndView mav = new ModelAndView("/space/form");
        mav.addObject("message", message);

        return mav;
    }

    @RequestMapping("/space/fileUpload")
    public ModelAndView fileUpload(Model model) {
        ModelAndView mav = new ModelAndView("/space/ajaxUpload");
        return mav;
    }

}

