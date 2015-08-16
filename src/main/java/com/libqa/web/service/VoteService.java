package com.libqa.web.service;

import com.libqa.web.domain.QaReply;

/**
 * Created by yong on 2015-05-25.
 *
 * @author yong
 */
public interface VoteService {
    boolean isNotVote(QaReply qaReply, Integer userId);

    void saveByQaReply(QaReply qaReply, Integer userId);

    boolean findByReplyIdAndUserIdAndIsVoteAndIsCancel(Integer replyId, int userId, boolean isVoted, boolean isCanceled);
}
