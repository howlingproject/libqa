package com.libqa.web.repository;

import com.libqa.web.domain.QaReply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by yong on 15. 2. 8..
 */
public interface QaReplyRepository extends JpaRepository<QaReply, Integer> {
    QaReply findByReplyIdAndIsDeletedFalse(Integer replyId);

    QaReply findByReplyIdAndIsDeleted(Integer replyId, boolean isDeleted);

    List<QaReply> findAllByQaIdAndIsDeletedOrderByOrderIdxDesc(Integer qaId, boolean isDeleted);

    List<QaReply> findAllByQaIdAndIsDeletedAndOrderIdxGreaterThanOrderByOrderIdxAsc(Integer qaId, boolean isDeleted, Integer orderIdx);

    List<QaReply> findAllByQaIdAndDepthIdxAndIsDeletedOrderByReplyIdAsc(Integer qaId, int depthIdx, boolean isDeleted);

    List<QaReply> findAllByQaIdAndParentsIdAndDepthIdxAndIsDeletedOrderByOrderIdxAsc(Integer qaId, Integer replyId, int depthIdx, boolean isDeleted);

    List<QaReply> findByQaIdAndIsDeletedFalse(Integer qaId);

    List<QaReply> findByUserIdAndIsDeleted(Integer userId, boolean isDeleted);

    Integer countByQaIdAndIsDeletedFalse(Integer qaId);
}
