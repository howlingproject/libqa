package com.libqa.application.enums;

public enum FeedActionType {
    FEED_LIKE,
    FEED_CLAIM,
    FEED_REPLY_LIKE,
    FEED_REPLY_CLAIM;

    public FeedThreadType getThreadType() {
        if (this == FEED_LIKE || this == FEED_CLAIM) {
            return FeedThreadType.FEED;
        }
        return FeedThreadType.FEED_REPLY;
    }
}
