package com.libqa.web.view.feed;

import com.libqa.web.domain.Feed;
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
     * feed에 의한 like 정보를 display용으로 build 한다
     *
     * @param feed
     * @param viewer
     * @return DisplayFeedAction
     */
    public DisplayFeedAction buildLike(Feed feed, User viewer) {
        FeedLike feedLikeActor = FeedLike.of(feed.getFeedId(), viewer);
        return build(feedLikeActor, feed.getLikeCount());
    }

    /**
     * feedReply에 의한 like 정보를 display용으로 build 한다
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
     * feed에 의한 claim 정보를 display용으로 build 한다
     *
     * @param feed
     * @param viewer
     * @return DisplayFeedAction
     */
    public DisplayFeedAction buildClaim(Feed feed, User viewer) {
        FeedClaim feedClaimActor = FeedClaim.of(feed.getFeedId(), viewer);
        return build(feedClaimActor, feed.getClaimCount());
    }

    /**
     * feedReply에 의한 claim 정보를 display용으로 build 한다
     *
     * @param feedReply
     * @param viewer
     * @return DisplayFeedAction
     */
    public DisplayFeedAction buildClaim(FeedReply feedReply, User viewer) {
        FeedReplyClaim feedReplyClaimActor = FeedReplyClaim.of(feedReply.getFeedReplyId(), viewer);
        return build(feedReplyClaimActor, feedReply.getClaimCount());
    }

    private DisplayFeedAction build(FeedActor feedActor, int actionCount) {
        if (actionCount == 0) {
            return new DisplayFeedAction(false, actionCount);
        }

        FeedAction feedAction = feedActionService.getFeedActionBy(feedActor);
        return new DisplayFeedAction(feedAction.isActed(), actionCount);
    }

}