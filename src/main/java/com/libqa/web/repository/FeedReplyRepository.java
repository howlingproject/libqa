package com.libqa.web.repository;

import com.libqa.web.domain.FeedThread;
import com.libqa.web.domain.FeedReply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedReplyRepository extends JpaRepository<FeedReply, Integer> {
    List<FeedReply> findByFeedThreadFeedThreadId(Integer feedThreadId);
    Integer countByFeedThreadAndIsDeletedFalse(FeedThread feedThread);
}
