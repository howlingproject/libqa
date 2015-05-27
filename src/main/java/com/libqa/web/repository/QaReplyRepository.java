package com.libqa.web.repository;

import com.libqa.web.domain.QaReply;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by yong on 15. 2. 8..
 */
public interface QaReplyRepository extends JpaRepository<QaReply, Long> {
    QaReply findByReplyId(Integer replyId, boolean isDeleted);
}
