package com.libqa.web.service.feed;

import com.google.common.collect.Iterables;
import com.libqa.web.domain.FeedAction;
import com.libqa.web.domain.User;
import com.libqa.web.repository.FeedActionRepository;
import com.libqa.web.service.feed.actor.FeedActor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

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
        FeedAction feedAction = getFeedAction(user, feedActor);
        if (feedAction.isActed()) {
            feedAction.cancelByUser(user);
        } else {
            createFeedAction(user, feedActor);
        }
    }

    public FeedAction getFeedAction(User user, FeedActor feedActor) {
        // TODO convert to queryDsl
        List<FeedAction> feedActionsByUser = feedActionRepository.findByFeedActorIdAndUserIdAndIsCanceledFalse(
                feedActor.getFeedActorId(), user.getUserId());

        return Iterables.tryFind(feedActionsByUser,
                input -> (input.getFeedActionType() == feedActor.getFeedActionType()
                        && input.getFeedThreadType() == feedActor.getFeedThreadType()
                )).or(FeedAction.notYetCreated());
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
