package com.libqa.web.service;

import com.libqa.web.domain.QaReply;
import com.libqa.web.domain.Vote;

/**
 * Created by yong on 2015-05-25.
 *
 * @author yong
 */
public interface VoteService {
    Vote findByReplyIdAndUserIdAndIsCancel(Integer replyId, Integer userId, boolean isCancel);

    void saveByQaReply(QaReply qaReply, Integer userId, boolean isVote);

    void deleteByQaReply(QaReply qaReply, Integer userId);

    boolean findByReplyIdAndUserIdAndIsVoteAndIsCancel(Integer replyId, int userId, boolean isVote, boolean isCancel);

}
