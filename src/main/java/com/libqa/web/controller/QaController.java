package com.libqa.web.controller;

import com.libqa.application.dto.QaDto;
import com.libqa.application.framework.ResponseData;
import com.libqa.web.domain.Keyword;
import com.libqa.web.domain.QaContent;
import com.libqa.web.domain.QaFile;
import com.libqa.web.domain.QaReply;
import com.libqa.web.service.*;
import com.libqa.web.view.DisplayQa;
import com.libqa.web.view.DisplayQaReply;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.*;

import static com.libqa.application.framework.ResponseData.createFailResult;
import static com.libqa.application.framework.ResponseData.createSuccessResult;

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
    QaReplyService qaReplyService;

    @Autowired
    QaFileService qaFileService;

    @Autowired
    KeywordService keywordService;

    @Autowired
    KeywordListService keywordListService;

    @RequestMapping("/qa")
    public String qa() {
        return "redirect:/qa/main";
    }

    @RequestMapping("/qa/main")
    public ModelAndView main(Model model){
        boolean isDeleted = false;

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
        mav.addObject("keywordList", keywordList);
        return mav;
    }

    @RequestMapping("/qa/edit/{qaId}")
    public ModelAndView edit(@PathVariable Integer qaId) {
        boolean isDeleted = false;

        QaContent qaContent =  qaService.findByQaId(qaId, isDeleted);
        List<Keyword> keywordList = keywordService.findByQaId(qaId, isDeleted);
        ModelAndView mav = new ModelAndView("qa/edit");
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
    public ResponseData<QaContent> save(@ModelAttribute QaContent paramQaContent, @ModelAttribute QaFile paramQaFiles) {
        ResponseData resultData = new ResponseData();

        QaContent qaContent = new QaContent();

        // TODO List 차후 로그인으로 변경
        paramQaContent.setUserId(1);
        paramQaContent.setUserNick("용퓌");
        paramQaContent.setInsertUserId(1);
        paramQaContent.setInsertDate(new Date());
        try {
            qaContent = qaService.saveWithKeyword(paramQaContent, paramQaFiles);
            return createSuccessResult(qaContent);
        } catch (Exception e) {
            return createFailResult(qaContent);
        }
    }

    @RequestMapping(value = "/qa/delete", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData<QaContent> delete(@RequestParam("qaId") Integer qaId) {
        ResponseData resultData = new ResponseData();

        QaContent qaContent = new QaContent();
        try {
            qaService.deleteWithKeyword(qaId);
            return createSuccessResult(qaContent);
        } catch (Exception e) {
            return createFailResult(qaContent);
        }
    }

    @RequestMapping(value = "/qa/saveReply", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData<QaReply> saveReply(QaReply qaReply) {

        // TODO List 차후 로그인으로 변경
        qaReply.setInsertDate(new Date());
        qaReply.setInsertUserId(1);
        qaReply.setUserId(1);
        qaReply.setUserNick("용퓌");

        QaReply newQaReply = qaReplyService.saveWithQaContent(qaReply);
        return createSuccessResult(newQaReply);
    }

    @RequestMapping(value ="/qa/saveChildReply", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData<QaReply> saveChildReply(QaReply qaReply){

        // TODO List 차후 로그인으로 변경
        qaReply.setInsertDate(new Date());
        qaReply.setInsertUserId(1);
        qaReply.setUserId(1);
        qaReply.setUserNick("용퓌");

        QaReply newQaReply = qaReplyService.saveChildReply(qaReply);
        return createSuccessResult(newQaReply);
    }

    @RequestMapping(value = "/qa/fileList", method = RequestMethod.GET)
    @ResponseBody
    public ResponseData<QaFile> fileList(@RequestParam("qaId") Integer qaId) throws IOException {
        boolean isDeleted = false;
        List<QaFile> qaFileList = new ArrayList<QaFile>();
        try {
            QaContent qaContent = qaService.findByQaId(qaId, isDeleted);
            qaFileList = qaContent.getQaFiles();
            return createSuccessResult(qaFileList);
        }catch (Exception e){
            e.printStackTrace();
            return createFailResult(qaFileList);
        }
    }

    @RequestMapping(value = "/qa/qaList", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData<DisplayQa> qaList(@ModelAttribute QaDto qaDto){
        boolean isDeleted = false;
        List<QaContent> qaContentList = new ArrayList<>();
        List<DisplayQa> displayQaList = new ArrayList<>();
        try {
            qaContentList = qaService.findByIsReplyedAndDayType(qaDto);
            for(QaContent qaContent : qaContentList) {
                displayQaList.add(new DisplayQa(qaContent, keywordService.findByQaId(qaContent.getQaId(), isDeleted)));
            }
            return createSuccessResult(displayQaList);
        }catch(Exception e){
            return createFailResult(displayQaList);
        }
    }

    @RequestMapping(value = "/qa/saveVoteUp", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData<QaReply> saveVoteUp(@ModelAttribute QaReply paramQaReply){
        QaReply qareply = qaReplyService.saveVoteUp(paramQaReply, 1); // TODO 로그인 처리

        return createSuccessResult(qareply);
    }

    @RequestMapping(value="/qa/replyList", method = RequestMethod.GET)
    @ResponseBody
    public ResponseData<DisplayQaReply> replyList(@RequestParam("qaId") Integer qaId){
        List<DisplayQaReply> qaReplyList = new ArrayList<>();
        List<QaReply> qaReplyFirstDepthList = qaReplyService.findByQaIdAndDepthIdx(qaId, 1);
        for (QaReply qaReply : qaReplyFirstDepthList) {
            List<QaReply> qaReplies = qaReplyService.findByQaIdAndParentsIdAndDepthIdx(qaReply.getQaId(), qaReply.getReplyId(), 2);
            qaReplyList.add(new DisplayQaReply(qaReply, qaReplies));
        }
        return createSuccessResult(qaReplyList);
    }

    @RequestMapping(value="/qa/reply/delete/{replyId}", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData<Integer> deleteReply(@PathVariable Integer replyId){
        try {
            qaReplyService.delete(replyId);
            return createSuccessResult(replyId);
        } catch (Exception e) {
            log.error("save reply error : {}", e);
            return createFailResult(replyId);
        }
    }
}
