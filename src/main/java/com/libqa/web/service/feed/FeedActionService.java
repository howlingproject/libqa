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
     * feedActor의 action을 처리한다.
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
     * feedActor의 FeedAction을 조회한다.
     * feedAction이 존재하지 않으면 {@code FeedAction.notYet()}이 리턴된다.
     *
     * @param feedActionActor feedActionActor of user
     * @return FeedAction
     */
    public FeedAction getFeedActionBy(FeedActionActor feedActionActor) {
        // TODO convert to queryDsl
        List<FeedAction> feedActionsByUser = feedActionRepository.findByFeedActorIdAndUserIdAndIsCanceledFalse(
                feedActionActor.getFeedActorId(), feedActionActor.getActionUser().getUserId());

        return Iterables.tryFind(feedActionsByUser,
                input -> (input.getFeedActionType() == feedActionActor.getFeedActionType()
                        && input.getFeedThreadType() == feedActionActor.getFeedThreadType()
                )).or(FeedAction.notYet());
    }

    public Integer countOf(FeedActionActor feedActionActor) {
        // TODO convert to queryDsl
        return feedActionRepository.countByFeedActorIdAndFeedThreadTypeAndFeedActionTypeAndIsCanceledFalse(
                feedActionActor.getFeedActorId(), feedActionActor.getFeedThreadType(), feedActionActor.getFeedActionType());
    }

    private FeedAction createFeedActionBy(FeedActionActor feedActionActor) {
        FeedAction feedAction = new FeedAction();
        feedAction.setFeedActorId(feedActionActor.getFeedActorId());
        feedAction.setFeedActionType(feedActionActor.getFeedActionType());
        feedAction.setFeedThreadType(feedActionActor.getFeedThreadType());
        feedAction.setUserId(feedActionActor.getActionUser().getUserId());
        feedAction.setUserNick(feedActionActor.getActionUser().getUserNick());
        feedAction.setInsertUserId(feedActionActor.getActionUser().getUserId());
        feedAction.setInsertDate(new Date());
        feedAction.setCanceled(false);
        feedActionRepository.save(feedAction);
        return feedAction;
    }

}
