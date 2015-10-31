package com.libqa.web.service;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.libqa.application.enums.FeedActionType;
import com.libqa.web.domain.FeedAction;
import com.libqa.web.repository.FeedActionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class FeedActionService {
    private static Predicate<FeedAction> IS_NOT_CANCEL(final FeedActionType feedActionType) {
        return input -> input.isNotCanceled() && input.getFeedActionType() == feedActionType;
    }

    @Autowired
    private FeedActionRepository feedActionRepository;

    public FeedAction create(int actorId, int userId, String userNick, FeedActionType feedActionType) {
        FeedAction feedAction = new FeedAction();
        feedAction.setFeedActorId(actorId);
        feedAction.setFeedActionType(feedActionType);
        feedAction.setUserId(userId);
        feedAction.setUserNick(userNick);
        feedAction.setInsertUserId(userId);
        feedAction.setUpdateUserId(userId);
        feedAction.setCanceled(false);
        feedAction.setInsertDate(new Date());
        feedAction.setUpdateDate(new Date());
        feedActionRepository.save(feedAction);
        return feedAction;
    }

    public FeedAction getFeedAction(int actionTypeId, int userId, FeedActionType feedActionType) {
        FeedAction feedAction = findFeedAction(actionTypeId, userId, feedActionType);
        if (feedAction == null) {
            return FeedAction.notYet();
        }
        return feedAction;
    }

    public Integer getCount(int actorId, FeedActionType actionType) {
        return feedActionRepository.countByFeedActorIdAndFeedActionTypeAndIsCanceledFalse(actorId, actionType);
    }

    private FeedAction findFeedAction(int actorId, int userId, FeedActionType actionType) {
        List<FeedAction> feedActions = feedActionRepository.findByFeedActorIdAndUserId(actorId, userId);
        return Iterables.tryFind(feedActions, IS_NOT_CANCEL(actionType)).orNull();
    }


}
