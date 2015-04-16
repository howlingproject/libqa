package com.libqa.web.controller;

import com.libqa.application.framework.ResponseData;
import com.libqa.web.domain.Keyword;
import com.libqa.web.domain.QaContent;
import com.libqa.web.domain.QaFile;
import com.libqa.web.domain.QaReply;
import com.libqa.web.service.KeywordService;
import com.libqa.web.service.QaFileService;
import com.libqa.web.service.QaReplyService;
import com.libqa.web.service.QaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.ArrayList;
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
    QaFileService qaFileService;

    @Autowired
    QaReplyService qaReplyService;

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
    public ModelAndView view(@PathVariable Integer qaId) {
        boolean isDeleted = false;

        QaContent qaContent =  qaService.findByQaId(qaId, isDeleted);
        List<Keyword> keywordList = keywordService.findByQaId(qaId, isDeleted);
        ModelAndView mav = new ModelAndView("qa/view");
        mav.addObject("qaContent", qaContent);
        mav.addObject("qaReplyList", qaContent.getQaReplys());
        mav.addObject("qaReplyCnt", qaContent.getQaReplys().size());
        mav.addObject("keywordList", keywordList);
        return mav;
    }

    @RequestMapping("/qa/form")
    public ModelAndView create(Model model){
        ModelAndView mav = new ModelAndView("qa/form");
        return mav;
    }

    @RequestMapping(value = "/qa/save", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData<QaContent> save(QaContent qaContent, QaFile qaFile){
        QaContent newQaContent = new QaContent();
        try {
            newQaContent = qaService.saveWithKeyword(qaContent, qaFile);
        } catch (Exception e){
            e.printStackTrace();
        }
        return ResponseData.createSuccessResult(newQaContent);
    }

    @RequestMapping(value = "/qa/saveReply", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData<QaReply> saveReply(QaReply qaReply){
        QaReply newQaReply = qaReplyService.saveWithQaContent(qaReply);
        return ResponseData.createSuccessResult(newQaReply);
    }

    @RequestMapping(value = "/qa/fileList", method = RequestMethod.GET)
    @ResponseBody
    public ResponseData<QaFile> fileList(@RequestParam("qaId") Integer qaId) throws IOException {
        boolean isDeleted = false;
        List<QaFile> qaFileList = new ArrayList<>();
        try {
            QaContent qaContent = qaService.findByQaId(qaId, isDeleted);
            qaFileList = qaContent.getQaFiles();
            return ResponseData.createSuccessResult(qaFileList);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseData.createFailResult(qaFileList);
        }
    }
}
