package com.libqa.web.controller;

import com.libqa.application.framework.ResponseData;
import com.libqa.domain.Space;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import com.libqa.application.enums.SpaceViewEnum;

import javax.validation.Valid;

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

    @RequestMapping(value = "/space/addSpace", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData<?> addSpace(@RequestBody Space space) {
        log.info("## space : {}", space);
        return null;
    }

}

