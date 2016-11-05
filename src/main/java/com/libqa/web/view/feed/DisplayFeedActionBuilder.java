package com.libqa.web.view.feed;

import com.libqa.web.domain.FeedThread;
import com.libqa.web.domain.FeedAction;
import com.libqa.web.domain.FeedReply;
import com.libqa.web.domain.User;
import com.libqa.web.service.feed.FeedActionService;
import com.libqa.web.service.feed.actor.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DisplayFeedActionBuilder {

    @Autowired
    private FeedActionService feedActionService;

    /**
     * feed like 정보를 display용으로 build 한다
     *
     * @param feedThread
     * @param viewer
     * @return DisplayFeedAction
     */
    public DisplayFeedAction buildLike(FeedThread feedThread, User viewer) {
        FeedThreadLike feedThreadLikeActor = FeedThreadLike.of(feedThread.getFeedThreadId(), viewer);
        return build(feedThreadLikeActor, feedThread.getLikeCount());
    }

    /**
     * feedReply like 정보를 display 용으로 build 한다
     *
     * @param feedReply
     * @param viewer
     * @return DisplayFeedAction
     */
    public DisplayFeedAction buildLike(FeedReply feedReply, User viewer) {
        FeedReplyLike feedReplyLikeActor = FeedReplyLike.of(feedReply.getFeedReplyId(), viewer);
        return build(feedReplyLikeActor, feedReply.getLikeCount());
    }

    /**
     * feed claim 정보를 display 용으로 build 한다
     *
     * @param feedThread
     * @param viewer
     * @return DisplayFeedAction
     */
    public DisplayFeedAction buildClaim(FeedThread feedThread, User viewer) {
        FeedThreadClaim feedThreadClaimActor = FeedThreadClaim.of(feedThread.getFeedThreadId(), viewer);
        return build(feedThreadClaimActor, feedThread.getClaimCount());
    }

    /**
     * feedReply claim 정보를 display 용으로 build 한다
     *
     * @param feedReply
     * @param viewer
     * @return DisplayFeedAction
     */
    public DisplayFeedAction buildClaim(FeedReply feedReply, User viewer) {
        FeedReplyClaim feedReplyClaimActor = FeedReplyClaim.of(feedReply.getFeedReplyId(), viewer);
        return build(feedReplyClaimActor, feedReply.getClaimCount());
    }

    private DisplayFeedAction build(FeedActionActor feedActionActor, int actionCount) {
        if (actionCount == 0) {
            return new DisplayFeedAction(false, actionCount);
        }

        FeedAction feedAction = feedActionService.getFeedActionBy(feedActionActor);
        return new DisplayFeedAction(feedAction.isActed(), actionCount);
    }

}