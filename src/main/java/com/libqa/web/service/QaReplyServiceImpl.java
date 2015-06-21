package com.libqa.web.service;

import com.libqa.web.domain.QaReply;
import com.libqa.web.repository.QaReplyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public QaReply saveWithQaContent(QaReply paramQaReply) {
        boolean isDeleted = false;
        List<QaReply> parentQaReplyList = qaReplyRepository.findAllByQaIdAndIsDeletedOrderByDepthIdxDesc(paramQaReply.getQaId(), isDeleted);
        QaReply parentQaReply = parentQaReplyList.get(0);
        paramQaReply.setOrderIdx(parentQaReply.getOrderIdx()+1);
        QaReply newQaReply = qaReplyRepository.save(paramQaReply);
        newQaReply.setParentsId(newQaReply.getReplyId());
        qaReplyRepository.flush();
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

}
