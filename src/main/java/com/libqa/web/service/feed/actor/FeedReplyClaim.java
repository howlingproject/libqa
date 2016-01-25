package com.libqa.web.service.feed.actor;

import com.libqa.application.enums.FeedActionType;
import com.libqa.application.enums.FeedThreadType;

public class FeedReplyClaim extends FeedActor {
    public FeedReplyClaim(int feedReplyId) {
        super(feedReplyId);
    }

    public static FeedReplyClaim of(int feedReplyId) {
        return new FeedReplyClaim(feedReplyId);
    }

    @Override
    public FeedThreadType getFeedThreadType() {
        return FeedThreadType.FEED_REPLY;
    }

    @Override
    public FeedActionType getFeedActionType() {
        return FeedActionType.CLAIM;
    }

}
