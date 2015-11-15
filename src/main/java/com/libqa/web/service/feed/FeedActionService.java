package com.libqa.web.service.feed;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.libqa.application.enums.FeedActionType;
import com.libqa.web.domain.FeedAction;
import com.libqa.web.domain.User;
import com.libqa.web.repository.FeedActionRepository;
import com.libqa.web.service.feed.actor.FeedActor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class FeedActionService {
    @Autowired
    private FeedActionRepository feedActionRepository;

    public FeedAction create(User user, FeedActor feedActor) {
        FeedAction feedAction = new FeedAction();
        feedAction.setFeedActorId(feedActor.getFeedActorId());
        feedAction.setFeedActionType(feedActor.getFeedActionType());
        feedAction.setFeedThreadType(feedActor.getFeedThreadType());
        feedAction.setUserId(user.getUserId());
        feedAction.setUserNick(user.getUserNick());
        feedAction.setInsertUserId(user.getUserId());
        feedAction.setUpdateUserId(user.getUserId());
        feedAction.setCanceled(false);
        feedAction.setInsertDate(new Date());
        feedAction.setUpdateDate(new Date());
        feedActionRepository.save(feedAction);
        return feedAction;
    }

    public FeedAction getFeedAction(User user, FeedActor feedActor) {
        FeedAction feedAction = findFeedAction(feedActor.getFeedActorId(), user.getUserId(), feedActor.getFeedActionType());
        if (feedAction == null) {
            return FeedAction.notYet();
        }
        return feedAction;
    }

    public Integer getCount(FeedActor feedActor) {
        return feedActionRepository.countByFeedActorIdAndFeedActionTypeAndIsCanceledFalse(feedActor.getFeedActorId(), feedActor.getFeedActionType());
    }

    private FeedAction findFeedAction(int actorId, int userId, FeedActionType actionType) {
        List<FeedAction> feedActions = feedActionRepository.findByFeedActorIdAndUserId(actorId, userId);
        return Iterables.tryFind(feedActions, IS_NOT_CANCEL(actionType)).orNull();
    }

    private static Predicate<FeedAction> IS_NOT_CANCEL(FeedActionType actionType) {
        return input -> input.isNotCanceled() && input.getFeedActionType() == actionType;
    }
}
