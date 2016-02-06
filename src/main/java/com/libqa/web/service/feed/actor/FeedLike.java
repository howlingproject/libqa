package com.libqa.web.service.feed.actor;

import com.libqa.application.enums.ActionType;
import com.libqa.application.enums.PostType;
import com.libqa.web.domain.User;

public class FeedLike extends FeedActionActor {
    private FeedLike(Integer feedThreadId, User actionUser) {
        super(feedThreadId, actionUser);
    }

    public static FeedLike of(Integer feedThreadId, User actionUser) {
        return new FeedLike(feedThreadId, actionUser);
    }

    @Override
    public PostType getPostType() {
        return PostType.THREAD;
    }

    @Override
    public ActionType getActionType() {
        return ActionType.LIKE;
    }
}
