package com.libqa.web.repository;

import com.libqa.web.domain.FeedAction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedActionRepository extends JpaRepository<FeedAction, Long> {
    List<FeedAction> findByFeedIdAndUserId(long feedId, Integer userId);
    List<FeedAction> findByFeedReplyIdAndUserId(long feedReplyId, Integer userId);
}
