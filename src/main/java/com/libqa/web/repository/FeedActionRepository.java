package com.libqa.web.repository;

import com.libqa.application.enums.FeedActionType;
import com.libqa.application.enums.FeedThreadType;
import com.libqa.web.domain.FeedAction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedActionRepository extends JpaRepository<FeedAction, Integer> {
    List<FeedAction> findByFeedActorIdAndUserIdAndIsCanceledFalse(Integer feedActorId, Integer userId);

    Integer countByFeedActorIdAndFeedThreadTypeAndFeedActionTypeAndIsCanceledFalse(
            Integer feedActorId, FeedThreadType threadType, FeedActionType actionType);
}
