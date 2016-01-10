package com.libqa.web.service.qa;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.libqa.web.domain.QaContent;
import com.libqa.web.domain.QaReply;
import com.libqa.web.domain.User;
import com.libqa.web.domain.Vote;
import com.libqa.web.repository.QaReplyRepository;
import com.libqa.web.service.user.UserService;
import com.libqa.web.view.qa.DisplayQaReply;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by yong on 2015-04-12.
 *
 * @author yong
 */
@Slf4j
@Service
public class QaReplyServiceImpl implements QaReplyService {

    @Autowired
    QaService qaService;

    @Autowired
    QaReplyRepository qaReplyRepository;

    @Autowired
    VoteService voteService;

    @Autowired
    UserService userService;

    @Override
    @Transactional
    public QaReply saveWithQaContent(QaReply paramQaReply) {
        boolean isDeleted = false;
        List<QaReply> parentQaReplyList = qaReplyRepository.findAllByQaIdAndIsDeletedOrderByOrderIdxDesc(paramQaReply.getQaId(), isDeleted);
        QaReply parentQaReply = Iterables.getFirst(parentQaReplyList, null);
        Integer orderIdx = parentQaReply == null ? 1 : parentQaReply.getOrderIdx() + 1;
        paramQaReply.setOrderIdx(orderIdx);
        QaReply newQaReply = qaReplyRepository.save(paramQaReply);

        // TODO List 차후 로그인으로 변경
        newQaReply.setParentsId(newQaReply.getReplyId());
        newQaReply.setUpdateDate(new Date());
        newQaReply.setUpdateUserId(1);
        qaService.saveIsReplyed(paramQaReply.getQaId(), true);
        return newQaReply;
    }

    @Override
    public QaReply saveVoteUp(QaReply paramQaReply, Integer userId) {
        return saveVote(paramQaReply, userId, "UP");
    }

    @Override
    public QaReply saveVoteDown(QaReply paramQaReply, Integer userId) {
        return saveVote(paramQaReply, userId, "DOWN");
    }

    public QaReply saveVote(QaReply paramQaReply, Integer userId, String voteType){
        boolean isDeleted = false;
        boolean isCancel = false;
        boolean isVote;
        boolean saveVoteValid = true;
        QaReply qaReply = qaReplyRepository.findByReplyIdAndIsDeleted(paramQaReply.getReplyId(), isDeleted);

        Vote vote = voteService.findByReplyIdAndUserIdAndIsCancel(paramQaReply.getReplyId(), userId, isCancel);
        int voteUpCount = qaReply.getVoteUpCount();
        int voteDownCount = qaReply.getVoteDownCount();

        if(vote != null){
            voteService.deleteByQaReply(qaReply, userId);
            if(vote.getIsVote()){
                voteUpCount -= 1;
            } else {
                voteDownCount -= 1;
            }
        }

        if("UP".equals(voteType)){
            isVote = true;
            voteUpCount += 1;
        } else {
            isVote = false;
            voteDownCount += 1;
        }
        voteService.saveByQaReply(qaReply, userId, isVote);

        qaReply.setVoteUpCount(voteUpCount);
        qaReply.setVoteDownCount(voteDownCount);
        qaReplyRepository.save(qaReply);

        return qaReply;
    }

    @Override
    public QaReply saveChildReply(QaReply paramQaReply) {
        boolean isDeleted = false;
//        updateOrderIdx(paramQaReply);

        paramQaReply.setContentsMarkup(paramQaReply.getContents());
        paramQaReply.setOrderIdx(paramQaReply.getOrderIdx()+1);
        paramQaReply.setDepthIdx(paramQaReply.getDepthIdx()+1);
        return qaReplyRepository.save(paramQaReply);
    }

    @Override
    public List<DisplayQaReply> findByQaIdAndDepthIdx(Integer qaId, int depthIdx) {
        boolean isDeleted = false;
        boolean isCancel = false;
        boolean vote = true;
        boolean notVote = false;
        int qaReplyDepth = 1;
        List<QaReply> qaReplyList = qaReplyRepository.findAllByQaIdAndDepthIdxAndIsDeletedOrderByReplyIdAsc(qaId, depthIdx, isDeleted);
        return makeDisplayQaReply(qaReplyList, qaReplyDepth);
    }

    public List<DisplayQaReply> makeDisplayQaReply(List<QaReply> qaReplyList, int qaReplyDepth){
        List<DisplayQaReply> displayQaReplyList = Lists.newArrayList();
        List<DisplayQaReply> displaySubQaReplyList = Lists.newArrayList();
        for(QaReply qaReply : qaReplyList){
            User writer = userService.findByUserId(qaReply.getUserId());
            boolean selfRecommend = voteService.hasRecommendUser(qaReply.getReplyId(), 1);
            boolean selfNonrecommend = voteService.hasNonRecommendUser(qaReply.getReplyId(), 1);
            if(1 == qaReplyDepth) {
                displaySubQaReplyList = findByQaIdAndParentsIdAndDepthIdx(qaReply.getQaId(), qaReply.getReplyId(), 2);
            }
            displayQaReplyList.add(new DisplayQaReply(qaReply, displaySubQaReplyList, selfRecommend, selfNonrecommend, writer));
        }
        return displayQaReplyList;
    }

    public List<DisplayQaReply> findByQaIdAndParentsIdAndDepthIdx(Integer qaId, Integer replyId, int depthIdx) {
        boolean isDeleted = false;
        List<QaReply> qaReplyList = qaReplyRepository.findAllByQaIdAndParentsIdAndDepthIdxAndIsDeletedOrderByOrderIdxAsc(qaId, replyId, depthIdx, isDeleted);
        int qaReplyDepth = 2;
        return makeDisplayQaReply(qaReplyList, qaReplyDepth);
    }

    @Override
    @Transactional
    public void delete(Integer replyId) {
        QaReply qaReply = qaReplyRepository.findOne(replyId);
        // TODO List 차후 로그인으로 변경
        qaReply.setDeleted(true);
        qaReply.setUpdateUserId(1);
        qaReply.setUpdateDate(new Date());
    }

    @Override
    public List<QaReply> findByQaId(Integer qaId) {
        return qaReplyRepository.findByQaId(qaId);
    }

    @Override
    public List<QaContent> findByUserId(Integer userId) {
        List<Integer> qaIds = new ArrayList<Integer>();
        List <QaReply> qaReplyList = qaReplyRepository.findByUserIdAndIsDeleted(userId, false);
        for(QaReply qaReply : qaReplyList){
            qaIds.add(qaReply.getQaId());
        }

        return qaService.findByQaIdIn(qaIds);
    }

    @Override
    public Integer countByQaContent(QaContent qaContent) {
        return qaReplyRepository.countByQaId(qaContent.getQaId());
    }

    public void updateOrderIdx(QaReply paramQaReply){
        boolean isDeleted = false;
        List<QaReply> updateTargetQaReplyList = qaReplyRepository.findAllByQaIdAndIsDeletedAndOrderIdxGreaterThanOrderByOrderIdxAsc(paramQaReply.getQaId(), isDeleted, paramQaReply.getOrderIdx());
        for(QaReply qaReply : updateTargetQaReplyList){
            qaReply.setOrderIdx(qaReply.getOrderIdx()+1);
            qaReplyRepository.flush();
        }
    }
}
