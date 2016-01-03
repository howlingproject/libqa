package com.libqa.web.view.feed;

import com.libqa.application.util.LoggedUser;
import com.libqa.web.domain.Feed;
import com.libqa.web.domain.FeedAction;
import com.libqa.web.domain.FeedReply;
import com.libqa.web.service.feed.FeedActionService;
import com.libqa.web.service.feed.actor.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DisplayFeedActionBuilder {

    @Autowired
    private LoggedUser loggedUser;
    @Autowired
    private FeedActionService feedActionService;

    public DisplayFeedAction buildLikeBy(Feed feed) {
        return build(FeedLike.of(feed.getFeedId()), feed.getLikeCount());
    }

    public DisplayFeedAction buildLikeBy(FeedReply feedReply) {
        return build(FeedReplyLike.of(feedReply.getFeedReplyId()), feedReply.getLikeCount());
    }

    public DisplayFeedAction buildClaimBy(Feed feed) {
        return build(FeedClaim.of(feed.getFeedId()), feed.getClaimCount());
    }

    public DisplayFeedAction buildClaimBy(FeedReply feedReply) {
        return build(FeedReplyClaim.of(feedReply.getFeedReplyId()), feedReply.getClaimCount());
    }

    private DisplayFeedAction build(FeedActor feedActor, int viewCount) {
        FeedAction feedAction = feedActionService.getFeedActionByUser(loggedUser.get(), feedActor);
        return new DisplayFeedAction(viewCount, feedAction.isActed());
    }

}