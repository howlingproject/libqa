package com.libqa.web.repository;

import com.libqa.application.enums.ActionType;
import com.libqa.application.enums.PostType;
import com.libqa.web.domain.FeedAction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedActionRepository extends JpaRepository<FeedAction, Integer> {
    List<FeedAction> findByFeedActorIdAndUserIdAndIsCanceledFalse(Integer feedActorId, Integer userId);

    Integer countByFeedActorIdAndPostTypeAndActionTypeAndIsCanceledFalse(
            Integer feedActorId, PostType postType, ActionType actionType);
}
