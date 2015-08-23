package com.libqa.web.service;

import com.libqa.web.domain.QaReply;
import com.libqa.web.domain.Vote;
import com.libqa.web.repository.VoteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by yong on 2015-05-25.
 *
 * @author yong
 */
@Slf4j
@Service
public class VoteServiceImpl implements VoteService {

    @Autowired
    VoteRepository voteRepository;

    @Override
    public Vote findByReplyIdAndUserIdAndIsCancel(Integer replyId, Integer userId, boolean isCancel) {
        return voteRepository.findByReplyIdAndUserIdAndIsCancel(replyId, userId, isCancel);
    }

    @Override
    public void saveByQaReply(QaReply qaReply, Integer userId, boolean isVote) {
        Vote vote = new Vote();
        vote.setReplyId(qaReply.getReplyId());
        vote.setInsertDate(new Date());
        vote.setUserNick("용퓌");
        vote.setIsVote(isVote);
        vote.setUserId(userId);

        voteRepository.save(vote);
    }

    @Override
    public void deleteByQaReply(QaReply qaReply, Integer userId) {
        boolean isCancel = false;
        Vote targetVote = voteRepository.findByReplyIdAndUserIdAndIsCancel(qaReply.getReplyId(), userId, isCancel);
        targetVote.setUpdateDate(new Date());
        targetVote.setCancel(true);
        voteRepository.flush();
    }


    @Override
    public Vote findByReplyIdAndUserIdAndIsVoteAndIsCancel(Integer replyId, int userId, boolean isVote, boolean isCanceled) {
        return voteRepository.findByReplyIdAndUserIdAndIsVoteAndIsCancel(replyId, userId, isVote, isCanceled);
    }

    @Override
    public boolean hasRecommendUser(Integer replyId, Integer userId) {
        Vote vote = voteRepository.findByReplyIdAndUserIdAndIsVoteAndIsCancel(replyId, userId, true, false);
        return null != vote;
    }

    @Override
    public boolean hasNonRecommendUser(Integer replyId, Integer userId) {
        Vote vote = voteRepository.findByReplyIdAndUserIdAndIsVoteAndIsCancel(replyId, userId, false, false);
        return null != vote;
    }
}
