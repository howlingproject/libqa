package com.libqa.web.controller;

import com.libqa.application.framework.ResponseData;
import com.libqa.web.domain.Keyword;
import com.libqa.web.domain.QaContent;
import com.libqa.web.service.KeywordService;
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

import java.util.List;

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

    @Autowired
    KeywordService keywordService;

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
        boolean isDeleted = false;

        QaContent qaContent =  qaService.findByQaId(qaId, isDeleted);
        List<Keyword> keywordList = keywordService.findByQaId(qaId, isDeleted);

        ModelAndView mav = new ModelAndView("qa/view");
        mav.addObject("qaContent", qaContent);
        mav.addObject("keyword", keywordList);
        return mav;
    }

    @RequestMapping("/qa/form")
    public ModelAndView create(Model model){
        ModelAndView mav = new ModelAndView("qa/form");
        return mav;
    }

    @RequestMapping(value = "/qa/save", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData<QaContent> save(QaContent qaContent){
        QaContent newQaContent = qaService.saveWithKeyword(qaContent);
        return ResponseData.createSuccessResult(newQaContent);
    }
}
