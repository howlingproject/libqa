package com.libqa.web.service.feed.actor;

import com.libqa.application.enums.ActionType;
import com.libqa.application.enums.PostType;
import com.libqa.web.domain.User;

public class FeedThreadLike extends FeedActionActor {
    private FeedThreadLike(Integer feedThreadId, User actionUser) {
        super(feedThreadId, actionUser);
    }

    public static FeedThreadLike of(Integer feedThreadId, User actionUser) {
        return new FeedThreadLike(feedThreadId, actionUser);
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
