package com.libqa.web.service.feed.actor;

import com.libqa.application.enums.FeedActionType;
import com.libqa.application.enums.FeedThreadType;
import com.libqa.web.domain.User;

public class FeedLike extends FeedActionActor {
    private FeedLike(Integer feedId, User actionUser) {
        super(feedId, actionUser);
    }

    public static FeedLike of(Integer feedId, User actionUser) {
        return new FeedLike(feedId, actionUser);
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
