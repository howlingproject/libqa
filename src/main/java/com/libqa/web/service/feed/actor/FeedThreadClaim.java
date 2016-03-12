package com.libqa.web.service.feed.actor;

import com.libqa.application.enums.ActionType;
import com.libqa.application.enums.PostType;
import com.libqa.web.domain.User;

public class FeedThreadClaim extends FeedActionActor {
    private FeedThreadClaim(Integer feedThreadId, User actionUser) {
        super(feedThreadId, actionUser);
    }

    public static FeedThreadClaim of(Integer feedThreadId, User actionUser) {
        return new FeedThreadClaim(feedThreadId, actionUser);
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
