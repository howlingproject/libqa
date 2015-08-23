package com.libqa.web.service;

import com.libqa.web.domain.QaReply;
import com.libqa.web.domain.Vote;
import com.libqa.web.repository.VoteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

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
    public void saveByQaReply(QaReply qaReply, Integer userId, boolean isVote) {
        Vote vote = new Vote();
        vote.setReplyId(qaReply.getReplyId());
        vote.setInsertDate(new Date());
        vote.setUserNick("용퓌");
        vote.setVote(isVote);
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
    public boolean findByReplyIdAndUserIdAndIsVoteAndIsCancel(Integer replyId, int userId, boolean isVote, boolean isCanceled) {
        List<Vote> voteList = voteRepository.findByReplyIdAndUserIdAndIsVoteAndIsCancel(replyId, userId, isVote, isCanceled);
        return !CollectionUtils.isEmpty(voteList);
    }
}
