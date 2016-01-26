package com.libqa.web.service.feed.actor;

import com.libqa.application.enums.FeedActionType;
import com.libqa.application.enums.FeedThreadType;
import com.libqa.web.domain.User;

public class FeedReplyClaim extends FeedActor {
    private FeedReplyClaim(Integer feedReplyId, User user) {
        super(feedReplyId, user);
    }

    public static FeedReplyClaim of(Integer feedReplyId, User user) {
        return new FeedReplyClaim(feedReplyId, user);
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
