package com.libqa.web.service.feed;

import com.google.common.collect.Iterables;
import com.libqa.web.domain.FeedAction;
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
     * 처음 요청인 경우 action 생성하고, 재요청인 경우 취소한다.
     *
     * @param feedActor feedActor of user
     */
    public void act(FeedActor feedActor) {
        FeedAction feedAction = getFeedActionBy(feedActor);
        if (feedAction.isNotYet()) {
            createFeedActionBy(feedActor);
        } else {
            feedAction.cancelByUser(feedActor.getActionUser());
        }
    }

    /**
     * feedActor의 FeedAction을 조회한다.
     * feedAction이 존재하지 않으면 {@code FeedAction.notYet()}이 리턴된다.
     *
     * @param feedActor feedActor of user
     * @return FeedAction
     */
    public FeedAction getFeedActionBy(FeedActor feedActor) {
        // TODO convert to queryDsl
        List<FeedAction> feedActionsByUser = feedActionRepository.findByFeedActorIdAndUserIdAndIsCanceledFalse(
                feedActor.getFeedActorId(), feedActor.getActionUser().getUserId());

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

    private FeedAction createFeedActionBy(FeedActor feedActor) {
        FeedAction feedAction = new FeedAction();
        feedAction.setFeedActorId(feedActor.getFeedActorId());
        feedAction.setFeedActionType(feedActor.getFeedActionType());
        feedAction.setFeedThreadType(feedActor.getFeedThreadType());
        feedAction.setUserId(feedActor.getActionUser().getUserId());
        feedAction.setUserNick(feedActor.getActionUser().getUserNick());
        feedAction.setInsertUserId(feedActor.getActionUser().getUserId());
        feedAction.setInsertDate(new Date());
        feedAction.setCanceled(false);
        feedActionRepository.save(feedAction);
        return feedAction;
    }

}
