package com.libqa.web.service.feed.actor;

import com.libqa.application.enums.FeedActionType;
import com.libqa.application.enums.FeedThreadType;
import com.libqa.web.domain.User;

public class FeedClaim extends FeedActor {
    private FeedClaim(Integer feedId, User user) {
        super(feedId, user);
    }

    public static FeedClaim of(Integer feedId, User user) {
        return new FeedClaim(feedId, user);
    }

    @Override
    public FeedThreadType getFeedThreadType() {
        return FeedThreadType.FEED;
    }

    @Override
    public FeedActionType getFeedActionType() {
        return FeedActionType.CLAIM;
    }
}
