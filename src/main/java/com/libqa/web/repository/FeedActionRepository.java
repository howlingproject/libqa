package com.libqa.web.repository;

import com.libqa.application.enums.FeedActionTypeEnum;
import com.libqa.web.domain.FeedAction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedActionRepository extends JpaRepository<FeedAction, Long> {
    List<FeedAction> findByFeedIdAndUserIdAndFeedActionType(Long feedId, Integer userId, FeedActionTypeEnum actionType);
}
