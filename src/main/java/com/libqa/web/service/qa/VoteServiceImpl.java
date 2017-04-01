package com.libqa.web.service.qa;

import com.libqa.web.domain.QaReply;
import com.libqa.web.domain.Vote;
import com.libqa.web.repository.VoteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

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
    public void saveByQaReply(QaReply qaReply, Integer userId, String userNick, boolean isVote) {
        Vote vote = new Vote();
        vote.setReplyId(qaReply.getReplyId());
        vote.setInsertDate(new Date());
        vote.setUserNick(userNick);
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

    @Override
    public List<Vote> findByReplyIdAndIsVote(Integer replyId, boolean isVote) {
        return voteRepository.findByReplyIdAndIsVoteAndIsCancelFalse(replyId, isVote);
    }

    @Override
    public int getCountByReplyId(Integer replyId) {
        return voteRepository.countByReplyIdAndIsCancelFalse(replyId);
    }

}
