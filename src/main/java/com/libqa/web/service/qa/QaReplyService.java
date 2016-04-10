package com.libqa.web.service.qa;

import com.libqa.web.domain.QaContent;
import com.libqa.web.domain.QaReply;
import com.libqa.web.domain.User;
import com.libqa.web.view.qa.DisplayQaReply;

import java.util.List;

/**
 * Created by yong on 2015-04-12.
 *
 * @author yong
 */
public interface QaReplyService {
    QaReply saveWithQaContent(QaReply qaReply, User user);

    QaReply saveVoteUp(QaReply paramQaReply, Integer userId);

    QaReply saveVoteDown(QaReply paramQaReply, Integer userId);

    QaReply saveChildReply(QaReply qaReply, User user);

    List<DisplayQaReply> findByQaIdAndDepthIdx(Integer qaId, int depthIdx, User viewer);

    void delete(Integer replyId, Integer userId);

    List<QaReply> findByQaId(Integer qaId);

    List<QaContent> findByUserId(Integer userId);

    Integer countByQaContent(QaContent qaContent);

    QaReply findByReplyId(Integer replyId);
}
