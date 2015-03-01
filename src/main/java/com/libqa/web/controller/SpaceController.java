package com.libqa.web.controller;

/**
 * Created by yion on 2015. 2. 8..
 */
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

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
        ModelAndView mav = new ModelAndView("form");
        mav.addObject("message", message);
        return mav;
    }
    @RequestMapping("/space/form1")
    public ModelAndView index1(Model model) {
        log.info("##############################");
        ModelAndView mav = new ModelAndView("space/form");
        mav.addObject("message", message);
        return mav;
    }
}
