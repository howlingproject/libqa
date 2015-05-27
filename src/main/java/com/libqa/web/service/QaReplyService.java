package com.libqa.web.service;

import com.libqa.web.domain.QaReply;

/**
 * Created by yong on 2015-04-12.
 *
 * @author yong
 */
public interface QaReplyService {
    QaReply saveWithQaContent(QaReply qaReply);

    QaReply saveVoteUp(QaReply paramQaReply, Integer userId);
}
