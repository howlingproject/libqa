package com.libqa.web.service;

import com.google.common.collect.Iterables;
import com.libqa.web.domain.QaReply;
import com.libqa.web.repository.QaReplyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        boolean isDeleted = false;
        QaReply qaReply = qaReplyRepository.findByReplyId(paramQaReply.getReplyId(), isDeleted);

        boolean notVote = voteService.isNotVote(qaReply, userId);
        if(!notVote){
            voteService.saveByQaReply(qaReply, userId);
        }
        qaReply.setVoteUpCount(qaReply.getVoteUpCount() + 1);

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
    public List<QaReply> findByQaIdAndDepthIdx(Integer qaId, int depthIdx) {
        boolean isDeleted = false;
        return qaReplyRepository.findAllByQaIdAndDepthIdxAndIsDeletedOrderByReplyIdAsc(qaId, depthIdx, isDeleted);
    }

    @Override
    public List<QaReply> findByQaIdAndParentsIdAndDepthIdx(Integer qaId, Integer replyId, int depthIdx) {
        boolean isDeleted = false;
        return qaReplyRepository.findAllByQaIdAndParentsIdAndDepthIdxAndIsDeletedOrderByOrderIdxAsc(qaId, replyId, depthIdx, isDeleted);
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

    public void updateOrderIdx(QaReply paramQaReply){
        boolean isDeleted = false;
        List<QaReply> updateTargetQaReplyList = qaReplyRepository.findAllByQaIdAndIsDeletedAndOrderIdxGreaterThanOrderByOrderIdxAsc(paramQaReply.getQaId(), isDeleted, paramQaReply.getOrderIdx());
        for(QaReply qaReply : updateTargetQaReplyList){
            qaReply.setOrderIdx(qaReply.getOrderIdx()+1);
            qaReplyRepository.flush();
        }
    }

}
