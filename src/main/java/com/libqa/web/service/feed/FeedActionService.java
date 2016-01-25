package com.libqa.web.service.feed;

import com.google.common.collect.Iterables;
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

    /**
     * feedActor의 action을 처리한다.
     *
     * @param user
     * @param feedActor
     */
    public void action(User user, FeedActor feedActor) {
        FeedAction feedAction = getFeedActionByUser(user, feedActor);
        if (feedAction.isNotYet()) {
            createFeedAction(user, feedActor);
        } else {
            feedAction.cancelByUser(user);
        }
    }

    public FeedAction getFeedActionByUser(User user, FeedActor feedActor) {
        // TODO convert to queryDsl
        List<FeedAction> feedActionsByUser = feedActionRepository.findByFeedActorIdAndUserIdAndIsCanceledFalse(
                feedActor.getFeedActorId(), user.getUserId());

        return Iterables.tryFind(feedActionsByUser,
                input -> (input.getFeedActionType() == feedActor.getFeedActionType()
                        && input.getFeedThreadType() == feedActor.getFeedThreadType()
                )).or(FeedAction.notYet());
    }

    public Integer countOf(FeedActor feedActor) {
        // TODO convert to queryDsl
        return feedActionRepository.countByFeedActorIdAndFeedThreadTypeAndFeedActionTypeAndIsCanceledFalse(
                feedActor.getFeedActorId(), feedActor.getFeedThreadType(), feedActor.getFeedActionType());
    }

    private FeedAction createFeedAction(User user, FeedActor feedActor) {
        FeedAction feedAction = new FeedAction();
        feedAction.setFeedActorId(feedActor.getFeedActorId());
        feedAction.setFeedActionType(feedActor.getFeedActionType());
        feedAction.setFeedThreadType(feedActor.getFeedThreadType());
        feedAction.setUserId(user.getUserId());
        feedAction.setUserNick(user.getUserNick());
        feedAction.setInsertDate(new Date());
        feedAction.setInsertUserId(user.getUserId());
        feedAction.setCanceled(false);
        feedActionRepository.save(feedAction);
        return feedAction;
    }


}
