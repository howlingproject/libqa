package com.libqa.web.controller;

import com.google.common.collect.Lists;
import com.libqa.application.dto.QaDto;
import com.libqa.application.framework.ResponseData;
import com.libqa.application.util.LoggedUserManager;
import com.libqa.web.domain.*;
import com.libqa.web.service.common.KeywordListService;
import com.libqa.web.service.common.KeywordService;
import com.libqa.web.service.qa.*;
import com.libqa.web.service.user.UserService;
import com.libqa.web.validator.QaValidator;
import com.libqa.web.view.qa.DisplayQa;
import com.libqa.web.view.qa.DisplayQaReply;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.libqa.application.enums.StatusCode.*;
import static com.libqa.application.framework.ResponseData.*;

/**
 * Created by yong on 2015-02-08.
 * @author yong
 */
@Slf4j
@Controller
public class QaController {

    @Autowired
    LoggedUserManager loggedUserManager;

    @Autowired
    QaService qaService;

    @Autowired
    QaReplyService qaReplyService;

    @Autowired
    QaRecommendService qaRecommendService;

    @Autowired
    QaFileService qaFileService;

    @Autowired
    KeywordService keywordService;

    @Autowired
    KeywordListService keywordListService;

    @Autowired
    VoteService voteService;

    @Autowired
    private UserService userService;

    @Autowired
    QaValidator qaValidator;

    @RequestMapping("/qa")
    public String qa() {
        return "redirect:/qa/main";
    }

    @RequestMapping("/qa/main")
    public ModelAndView main(Model model){
	    Integer qaTotalCount = qaService.getQaTotalCount();
	    Integer qaNotReplyedCount = qaService.getQaNotReplyedCount();
        ModelAndView mav = new ModelAndView("qa/main");
	    mav.addObject("qaTotalCount", qaTotalCount);
	    mav.addObject("qaNotReplyedCount", qaNotReplyedCount);
	    mav.addObject("recentQAContents", buildDisplayQas(qaService.getRecentQAContents()));
	    mav.addObject("waitReplyQAContents", buildDisplayQas(qaService.getWaitReplyQaContents()));
	    mav.addObject("bestQAContents", buildDisplayQas(qaService.getBestQaContents()));
        return mav;
    }

    @RequestMapping(value = "/qa/myWriteQaList", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData<DisplayQa> myQaList(@ModelAttribute QaDto qaDto){
        try {
            List<QaContent> qaContentList = qaService.findByUserId(loggedUserManager.getUser().getUserId());
            return createSuccessResult(buildDisplayQas(qaContentList));
        }catch(Exception e){
            return createFailResult(Lists.newArrayList());
        }
    }

    @RequestMapping(value = "/qa/myReplyQaList", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData<DisplayQa> myReplyQaList(@ModelAttribute QaDto qaDto){
        try {
            List<QaContent> qaContentList = qaReplyService.findByUserId(loggedUserManager.getUser().getUserId());
            return createSuccessResult(buildDisplayQas(qaContentList));
        }catch(Exception e){
            return createFailResult(Lists.newArrayList());
        }
    }

    private List<DisplayQa> buildDisplayQas(List<QaContent> qaContentList) {
        List<DisplayQa> displayQaList = Lists.newArrayList();
        for(QaContent qaContent : qaContentList) {
            User writer = userService.findByUserId(qaContent.getUserId());
            displayQaList.add(new DisplayQa(qaContent, writer, keywordService.findByQaId(qaContent.getQaId()), qaReplyService.findByQaId(qaContent.getQaId()) ));
        }
        return displayQaList;
    }

    @RequestMapping(value = "/qa/myRecommendQaList", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData<DisplayQa> myRecommendQaList(@ModelAttribute QaDto qaDto){
        boolean isDeleted = false;
        List<DisplayQa> displayQaList = Lists.newArrayList();
        try {
            List<QaRecommend> qaRecommendList = qaRecommendService.findByUserIdAndIsCommendTrue(loggedUserManager.getUser().getUserId());
            for(QaRecommend qaRecommend : qaRecommendList) {
                User recommender = userService.findByUserId(qaRecommend.getUserId());
                QaContent qaContent = qaService.findByQaId(qaRecommend.getQaId(), isDeleted);
                displayQaList.add(new DisplayQa(qaContent, recommender, keywordService.findByQaId(qaRecommend.getQaId()), qaReplyService.findByQaId(qaRecommend.getQaId()) ));
            }
            return createSuccessResult(displayQaList);
        }catch(Exception e){
            return createFailResult(displayQaList);
        }
    }

    @RequestMapping("/qa/{qaId}")
    public ModelAndView view(@PathVariable Integer qaId) {
        boolean isDeleted = false;
        User user = loggedUserManager.getUser();
        QaContent qaContent = qaService.view(qaId);
        List<QaRecommend> qaRecommendList = qaRecommendService.findByQaIdAndIsCommend(qaId, true);
        List<QaRecommend> qaNonRecommendList = qaRecommendService.findByQaIdAndIsCommend(qaId, false);
	    DisplayQa displayQa = new DisplayQa(qaContent, qaRecommendList, qaNonRecommendList, user.getUserId());
        User writer = userService.findByUserId(qaContent.getUserId());
        List<Keyword> keywordList = keywordService.findByQaId(qaId);

        ModelAndView mav = new ModelAndView("qa/view");
        mav.addObject("qaContent", displayQa);
        mav.addObject("writer", writer);
        mav.addObject("keywordList", keywordList);
        mav.addObject("loggedUser", loggedUserManager.getUser());
        return mav;
    }

    @RequestMapping("/qa/edit/{qaId}")
    public ModelAndView edit(@PathVariable Integer qaId) {
        boolean isDeleted = false;

        QaContent qaContent =  qaService.findByQaId(qaId, isDeleted);
        List<Keyword> keywordList = keywordService.findByQaId(qaId);
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
    public ResponseData<QaContent> save(@ModelAttribute QaContent requestQaContent, @ModelAttribute QaFile requestQaFiles, @ModelAttribute Keyword requestKeywords) {
        User user = loggedUserManager.getUser();
        QaContent qaContent = new QaContent();
        try {
            qaContent = qaService.saveWithKeyword(requestQaContent, requestQaFiles, requestKeywords, user);
            return createSuccessResult(qaContent);
        } catch (Exception e) {
            return createFailResult(qaContent);
        }
    }

    @RequestMapping(value = "/qa/update", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData<QaContent> update(@ModelAttribute QaContent requestQaContent, @ModelAttribute QaFile requestQaFiles, @ModelAttribute Keyword requestKeywords) {
        User user = loggedUserManager.getUser();
        boolean isDeleted = false;
        try {
            QaContent originQaContent = qaService.findByQaId(requestQaContent.getQaId(), isDeleted);
            if(user.isNotMatchUser(originQaContent.getUserId())){
                return createResult(NOT_MATCH_USER);
            }
            QaContent savedQaContent = qaService.updateWithKeyword(originQaContent, requestQaContent, requestQaFiles, requestKeywords, user);
            return createSuccessResult(savedQaContent);
        } catch (Exception e) {
            return createFailResult(null);
        }
    }

    @RequestMapping(value = "/qa/delete", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData<String> delete(@RequestParam("qaId") Integer qaId) {
        User user = loggedUserManager.getUser();
        try {
            if(qaValidator.isNotMatchUser(qaId, user)){
                return createFailResult(NOT_MATCH_USER.getComment());
            }
            if(qaValidator.checkQaContentDelete(qaId)){
                return createFailResult(EXIST_REPLY.getComment());
            }
            qaService.deleteWithKeyword(qaId, user.getUserId());
            return createSuccessResult(SUCCESS.getComment());
        } catch (Exception e) {
            return createFailResult(null);
        }
    }



    @RequestMapping(value = "/qa/saveReply", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData<QaReply> saveReply(QaReply qaReply) {
	    User user = loggedUserManager.getUser();
        QaReply newQaReply = qaReplyService.saveWithQaContent(qaReply, user);
        return createSuccessResult(newQaReply);
    }

    @RequestMapping(value ="/qa/saveChildReply", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData<QaReply> saveChildReply(QaReply qaReply){
        User user = loggedUserManager.getUser();
        QaReply newQaReply = qaReplyService.saveChildReply(qaReply, user);
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
                User writer = userService.findByUserId(qaContent.getUserId());
                displayQaList.add(new DisplayQa(qaContent, writer, keywordService.findByQaId(qaContent.getQaId()), qaReplyService.findByQaId(qaContent.getQaId()) ));
            }
            return createSuccessResult(displayQaList);
        }catch(Exception e){
            return createFailResult(displayQaList);
        }
    }

    @RequestMapping(value = "/qa/saveRecommend", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData<DisplayQa> saveRecommend(@ModelAttribute QaRecommend paramQaRecommend){
        User user = loggedUserManager.getUser();
        try {
            if (user.isGuest()) {
                return createResult(NEED_LOGIN);
            }
	        QaContent qaContent = qaRecommendService.saveRecommend(paramQaRecommend, user.getUserId(), user.getUserNick());
            List<QaRecommend> qaRecommendList = qaRecommendService.findByQaIdAndIsCommend(qaContent.getQaId(), true);
            List<QaRecommend> qaNonRecommendList = qaRecommendService.findByQaIdAndIsCommend(qaContent.getQaId(), false);
            DisplayQa displayQa = new DisplayQa(qaContent, qaRecommendList, qaNonRecommendList, user.getUserId());
            return createSuccessResult(displayQa);
        } catch (Exception e){
            return createFailResult(null);
        }
    }

    @RequestMapping(value = "/qa/saveVoteUp", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData<QaReply> saveVoteUp(@ModelAttribute QaReply paramQaReply){
        User user = loggedUserManager.getUser();
        try {
            if (user.isGuest()) {
                return createResult(NEED_LOGIN);
            }
            QaReply qareply = qaReplyService.saveVoteUp(paramQaReply, user.getUserId(), user.getUserNick());
            return createSuccessResult(qareply);
        } catch (Exception e){
            return createFailResult(null);
        }
    }

    @RequestMapping(value = "/qa/saveVoteDown", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData<QaReply> saveVoteDown(@ModelAttribute QaReply paramQaReply){
        User user = loggedUserManager.getUser();
        try {
            if (user.isGuest()) {
                return createResult(NEED_LOGIN);
            }
            QaReply qareply = qaReplyService.saveVoteDown(paramQaReply, user.getUserId(), user.getUserNick());
            return createSuccessResult(qareply);
        } catch (Exception e){
            return createFailResult(null);
        }
    }

    @RequestMapping(value="/qa/replyList", method = RequestMethod.GET)
    @ResponseBody
    public ResponseData<DisplayQaReply> replyList(@RequestParam("qaId") Integer qaId){
	    User viewer = loggedUserManager.getUser();
        List<DisplayQaReply> qaReplyList = qaReplyService.findByQaIdAndDepthIdx(qaId, 1, viewer);
        return createSuccessResult(qaReplyList);
    }

    @RequestMapping(value="/qa/reply/delete/{replyId}", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData<Integer> deleteReply(@PathVariable Integer replyId){
	    User user = loggedUserManager.getUser();
        try {
            QaReply originQaReply = qaReplyService.findByReplyId(replyId);
            if(user.isNotMatchUser(originQaReply.getUserId())){
                return createResult(NOT_MATCH_USER);
            }
            qaReplyService.delete(replyId, user.getUserId());
            return createSuccessResult(replyId);
        } catch (Exception e) {
            log.error("save reply error : {}", e);
            return createFailResult(replyId);
        }
    }

    @RequestMapping(value="/qa/recommend", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData<QaRecommend> recommendDetail(@ModelAttribute QaRecommend paramQaRecommend){
        User user = loggedUserManager.getUser();
        try{
            if(user.isGuest()){
                return createResult(NEED_LOGIN);
            }
            QaRecommend qaRecommend = qaRecommendService.findByQaIdAndUserIdAndIsCommend(paramQaRecommend.getQaId(), user.getUserId(), paramQaRecommend.getIsCommend());
            return createSuccessResult(qaRecommend);
        } catch (Exception e) {
            return createFailResult(null);
        }
    }

    @RequestMapping(value="/qa/reply/vote", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData<Vote> voteDetail(@ModelAttribute Vote paramVote){
        User user = loggedUserManager.getUser();
        boolean isCancel = false;
        try{
            if(user.isGuest()){
                return createResult(NEED_LOGIN);
            }
            Vote vote = voteService.findByReplyIdAndUserIdAndIsVoteAndIsCancel(paramVote.getReplyId(), user.getUserId(), paramVote.getIsVote(), isCancel);
            return createSuccessResult(vote);
        } catch (Exception e) {
            return createFailResult(null);
        }
    }
}
