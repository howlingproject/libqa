package com.libqa.web.service.qa;

import com.libqa.web.domain.QaContent;
import com.libqa.web.domain.QaReply;
import com.libqa.web.view.DisplayQaReply;

import java.util.List;

/**
 * Created by yong on 2015-04-12.
 *
 * @author yong
 */
public interface QaReplyService {
    QaReply saveWithQaContent(QaReply qaReply);

    QaReply saveVoteUp(QaReply paramQaReply, Integer userId);

    QaReply saveVoteDown(QaReply paramQaReply, Integer userId);

    QaReply saveChildReply(QaReply qaReply);

    List<DisplayQaReply> findByQaIdAndDepthIdx(Integer qaId, int depthIdx);

    void delete(Integer replyId);

    List<QaReply> findByQaId(Integer qaId);

    List<QaContent> findByUserId(Integer userId);
}
