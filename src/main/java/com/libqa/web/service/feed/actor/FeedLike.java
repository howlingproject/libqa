package com.libqa.web.service.feed.actor;

import com.libqa.application.enums.FeedActionType;
import com.libqa.application.enums.FeedThreadType;

public class FeedLike extends FeedActor {
    public FeedLike(int feedId) {
        super(feedId);
    }

    public static FeedLike of(int feedId) {
        return new FeedLike(feedId);
    }

    @Override
    public FeedThreadType getFeedThreadType() {
        return FeedThreadType.FEED;
    }

    @Override
    public FeedActionType getFeedActionType() {
        return FeedActionType.FEED_LIKE;
    }
}
