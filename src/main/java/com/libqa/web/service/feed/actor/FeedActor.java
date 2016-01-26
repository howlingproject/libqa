package com.libqa.web.service.feed.actor;

import com.libqa.application.enums.FeedActionType;
import com.libqa.application.enums.FeedThreadType;
import com.libqa.web.domain.User;
import lombok.Getter;

public abstract class FeedActor {
    @Getter
    private Integer feedActorId;

    @Getter
    private User actionUser;

    /**
     * @param feedActorId 각 actor의 unique key를 나타낸다. <br />
     *                    feed -> feedId, feedReploy -> feedReplyId
     * @param actionUser        action을 취한 user
     */
    FeedActor(Integer feedActorId, User actionUser) {
        this.feedActorId = feedActorId;
        this.actionUser = actionUser;
    }

    public abstract FeedThreadType getFeedThreadType();

    public abstract FeedActionType getFeedActionType();
}
