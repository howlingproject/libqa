package com.libqa.web.service;

import com.libqa.web.domain.QaReply;

import java.util.List;

/**
 * Created by yong on 2015-04-12.
 *
 * @author yong
 */
public interface QaReplyService {
    QaReply saveWithQaContent(QaReply qaReply);

    QaReply saveVoteUp(QaReply paramQaReply, Integer userId);

    QaReply saveChildReply(QaReply qaReply);

    List<QaReply> findByQaIdAndDepthIdx(Integer qaId, int depthIdx);

    List<QaReply> findByQaIdAndParentsIdAndDepthIdx(Integer qaId, Integer replyId, int depthIdx);

    void delete(Integer replyId);
}
