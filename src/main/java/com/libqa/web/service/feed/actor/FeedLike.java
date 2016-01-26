package com.libqa.web.service.feed.actor;

import com.libqa.application.enums.FeedActionType;
import com.libqa.application.enums.FeedThreadType;
import com.libqa.web.domain.User;

public class FeedLike extends FeedActor {
    private FeedLike(Integer feedId, User user) {
        super(feedId, user);
    }

    public static FeedLike of(Integer feedId, User user) {
        return new FeedLike(feedId, user);
    }

    @Override
    public FeedThreadType getFeedThreadType() {
        return FeedThreadType.FEED;
    }

    @Override
    public FeedActionType getFeedActionType() {
        return FeedActionType.LIKE;
    }
}
