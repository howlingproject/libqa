package com.libqa.web.controller;

import com.libqa.application.framework.ResponseData;
import com.libqa.web.domain.QaContent;
import com.libqa.web.service.QaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by yong on 2015-02-08.
 *
 * @author yong
 */
@Slf4j
@Controller
public class QaController {
    @Autowired
    QaService service;

    @RequestMapping("/qa")
    public String qa() {
        return "redirect:/qa/main";
    }

    @RequestMapping("/qa/main")
    public ModelAndView main(Model model){
        ModelAndView mav = new ModelAndView("qa/main");
        return mav;
    }

    @RequestMapping("/qa/create")
    public ModelAndView create(Model model){
        ModelAndView mav = new ModelAndView("qa/create");
        return mav;
    }

    @RequestMapping(value = "/qa/save", method = RequestMethod.POST)
    public ResponseData<?> save(QaContent qaContent){
        QaContent newQaContent = service.saveQaContentAndKeyword(qaContent);
        return ResponseData.createSuccessResult(newQaContent);
    }
}