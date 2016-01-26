package com.libqa.web.service.feed.actor;

import com.libqa.application.enums.FeedActionType;
import com.libqa.application.enums.FeedThreadType;
import com.libqa.web.domain.User;

public class FeedReplyLike extends FeedActor {
    private FeedReplyLike(Integer feedReplyId, User actionUser) {
        super(feedReplyId, actionUser);
    }

    public static FeedReplyLike of(Integer feedReplyId, User actionUser) {
        return new FeedReplyLike(feedReplyId, actionUser);
    }

    @Override
    public FeedThreadType getFeedThreadType() {
        return FeedThreadType.FEED_REPLY;
    }

    @Override
    public FeedActionType getFeedActionType() {
        return FeedActionType.LIKE;
    }
}
