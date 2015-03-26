package com.libqa.web.controller;

import com.libqa.application.framework.ResponseData;
import com.libqa.web.domain.QaContent;
import com.libqa.web.service.QaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
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
    QaService qaService;

    @RequestMapping("/qa")
    public String qa() {
        return "redirect:/qa/main";
    }

    @RequestMapping("/qa/main")
    public ModelAndView main(Model model){
        ModelAndView mav = new ModelAndView("qa/main");
        return mav;
    }

    @RequestMapping("/qa/{qaId}")
    public ModelAndView view(@PathVariable Integer qaId, Model model) {
        QaContent qaContent =  qaService.findById(qaId);

        ModelAndView mav = new ModelAndView("qa/view");
        mav.addObject("qaContent", qaContent);
        return mav;
    }

    @RequestMapping("/qa/create")
    public ModelAndView create(Model model){
        ModelAndView mav = new ModelAndView("qa/create");
        return mav;
    }

    @RequestMapping(value = "/qa/save", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData<QaContent> save(QaContent qaContent){
        QaContent newQaContent = qaService.saveWithKeyword(qaContent);
        return ResponseData.createSuccessResult(newQaContent);
    }
}
