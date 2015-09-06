package com.libqa.web.repository;

import com.libqa.application.enums.FeedActionTypeEnum;
import com.libqa.web.domain.FeedAction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedActionRepository extends JpaRepository<FeedAction, Integer> {
    List<FeedAction> findByFeedIdAndUserId(Integer feedId, Integer userId);
    List<FeedAction> findByFeedReplyIdAndUserId(Integer feedReplyId, Integer userId);
    Integer countByFeedIdAndFeedActionTypeAndIsCanceled(Integer feedId, FeedActionTypeEnum feedActionType, boolean isCanceled);
    Integer countByFeedReplyIdAndFeedActionTypeAndIsCanceled(Integer feedReplyId, FeedActionTypeEnum feedActionType, boolean isCanceled);
}
