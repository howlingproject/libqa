package com.libqa.web.service.feed.actor;

import com.libqa.application.enums.FeedActionType;
import com.libqa.application.enums.FeedThreadType;
import lombok.Getter;

public abstract class FeedActor {
    @Getter
    private int feedActorId;

    protected FeedActor(int feedActorId) {
        this.feedActorId = feedActorId;
    }

    public abstract FeedThreadType getFeedThreadType();

    public abstract FeedActionType getFeedActionType();
}
