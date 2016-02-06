package com.libqa.web.service.feed.actor;

import com.libqa.application.enums.ActionType;
import com.libqa.application.enums.PostType;
import com.libqa.web.domain.User;

public class FeedClaim extends FeedActionActor {
    private FeedClaim(Integer feedThreadId, User actionUser) {
        super(feedThreadId, actionUser);
    }

    public static FeedClaim of(Integer feedThreadId, User actionUser) {
        return new FeedClaim(feedThreadId, actionUser);
    }

    @Override
    public PostType getPostType() {
        return PostType.THREAD;
    }

    @Override
    public ActionType getActionType() {
        return ActionType.CLAIM;
    }
}
