package com.libqa.web.service.feed.actor;

import com.libqa.application.enums.FeedActionType;
import com.libqa.application.enums.FeedThreadType;
import com.libqa.web.domain.User;

public class FeedReplyClaim extends FeedActionActor {
    private FeedReplyClaim(Integer feedReplyId, User actionUser) {
        super(feedReplyId, actionUser);
    }

    public static FeedReplyClaim of(Integer feedReplyId, User actionUser) {
        return new FeedReplyClaim(feedReplyId, actionUser);
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
