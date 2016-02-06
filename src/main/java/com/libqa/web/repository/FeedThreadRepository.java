package com.libqa.web.repository;

import com.libqa.web.domain.FeedThread;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedThreadRepository extends JpaRepository<FeedThread, Integer> {
    List<FeedThread> findByIsDeletedFalse(Pageable pageable);
    List<FeedThread> findByFeedThreadIdLessThanAndIsDeletedFalse(Integer feedThreadId, Pageable pageable);
    List<FeedThread> findByUserIdAndIsDeletedFalse(Integer userId, Pageable pageable);
    List<FeedThread> findByUserIdAndFeedThreadIdLessThanAndIsDeletedFalse(Integer userId, Integer feedThreadId, Pageable pageable);
    Integer countFeedRepliesByFeedThreadId(Integer feedThreadId);
}
