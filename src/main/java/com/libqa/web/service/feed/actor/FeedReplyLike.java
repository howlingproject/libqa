package com.libqa.web.service.feed.actor;

import com.libqa.application.enums.FeedActionType;
import com.libqa.application.enums.FeedThreadType;
import com.libqa.web.domain.User;

public class FeedReplyLike extends FeedActor {
    private FeedReplyLike(Integer feedReplyId, User user) {
        super(feedReplyId, user);
    }

    public static FeedReplyLike of(Integer feedReplyId, User user) {
        return new FeedReplyLike(feedReplyId, user);
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
