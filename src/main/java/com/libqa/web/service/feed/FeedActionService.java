package com.libqa.web.service.feed;

import com.google.common.collect.Iterables;
import com.libqa.web.domain.FeedAction;
import com.libqa.web.repository.FeedActionRepository;
import com.libqa.web.service.feed.actor.FeedActionActor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class FeedActionService {
    @Autowired
    private FeedActionRepository feedActionRepository;

    /**
     * feedActionActor 의 action 을 처리한다.
     * 처음 요청인 경우 action 생성하고, 재요청인 경우 취소한다.
     *
     * @param feedActionActor feedActionActor of user
     */
    public void act(FeedActionActor feedActionActor) {
        FeedAction feedAction = getFeedActionBy(feedActionActor);
        if (feedAction.isNotYet()) {
            createFeedActionBy(feedActionActor);
        } else {
            feedAction.cancelByUser(feedActionActor.getActionUser());
        }
    }

    /**
     * feedAction의 카운트를 조회한다.
     *
     * @param feedActionActor
     * @return count of feed action
     */
    public Integer countOf(FeedActionActor feedActionActor) {
        return feedActionRepository.countByFeedActorIdAndPostTypeAndActionTypeAndIsCanceledFalse(
                feedActionActor.getFeedActorId(),
                feedActionActor.getPostType(),
                feedActionActor.getActionType());
    }

    /**
     * feedActionActor 의 FeedAction 을 조회한다.
     * feedAction 이 존재하지 않으면 {@code FeedAction.notYet()}이 리턴된다.
     *
     * @param feedActionActor feedActionActor of user
     * @return FeedAction
     */
    public FeedAction getFeedActionBy(FeedActionActor feedActionActor) {
        List<FeedAction> feedActionsByUser = feedActionRepository.findByFeedActorIdAndUserIdAndIsCanceledFalse(
                feedActionActor.getFeedActorId(),
                feedActionActor.getActionUser().getUserId());

        return Iterables.tryFind(feedActionsByUser,
                action -> hasActedByUser(feedActionActor, action))
                .or(FeedAction.notYet());
    }

    private boolean hasActedByUser(FeedActionActor feedActionActor, FeedAction action) {
        return feedActionActor.getActionType() == action.getActionType()
                && feedActionActor.getPostType() == action.getPostType();
    }

    private FeedAction createFeedActionBy(FeedActionActor feedActionActor) {
        FeedAction feedAction = new FeedAction();
        feedAction.setFeedActorId(feedActionActor.getFeedActorId());
        feedAction.setActionType(feedActionActor.getActionType());
        feedAction.setPostType(feedActionActor.getPostType());
        feedAction.setUserId(feedActionActor.getActionUser().getUserId());
        feedAction.setUserNick(feedActionActor.getActionUser().getUserNick());
        feedAction.setInsertUserId(feedActionActor.getActionUser().getUserId());
        feedAction.setInsertDate(new Date());
        feedAction.setCanceled(false);
        feedActionRepository.save(feedAction);
        return feedAction;
    }

}
