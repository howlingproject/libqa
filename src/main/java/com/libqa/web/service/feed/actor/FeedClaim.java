package com.libqa.web.service.feed.actor;

import com.libqa.application.enums.FeedActionType;
import com.libqa.application.enums.FeedThreadType;

public class FeedClaim extends FeedActor {
    public FeedClaim(int feedId) {
        super(feedId);
    }

    public static FeedClaim of(int feedId) {
        return new FeedClaim(feedId);
    }

    @Override
    public FeedThreadType getFeedThreadType() {
        return FeedThreadType.FEED;
    }

    @Override
    public FeedActionType getFeedActionType() {
        return FeedActionType.FEED_CLAIM;
    }
}
