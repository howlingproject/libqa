package com.libqa.web.service;

import com.libqa.application.enums.FeedActionType;
import com.libqa.web.domain.Feed;
import com.libqa.web.domain.FeedAction;
import com.libqa.web.domain.FeedReply;
import com.libqa.web.domain.User;

import java.util.Date;

import static com.libqa.application.enums.FeedActionType.*;

public class FeedActionFactory {

    public static FeedAction createLike(Feed feed, User user) {
        return createFeedAction(feed, user, FEED_LIKE);
    }

    public static FeedAction createClaim(Feed feed, User user) {
        return createFeedAction(feed, user, FEED_CLAIM);
    }

    public static FeedAction createLike(FeedReply feedReply, User user) {
        return createFeedAction(feedReply, user, FEED_REPLY_LIKE);
    }

    public static FeedAction createClaim(FeedReply feedReply, User user) {
        return createFeedAction(feedReply, user, FEED_REPLY_CLAIM);
    }

    private static FeedAction createFeedAction(Feed feed, User user, FeedActionType feedActionType) {
        FeedAction feedAction = new FeedAction();
        feedAction.setFeedId(feed.getFeedId());
        feedAction.setUserId(user.getUserId());
        feedAction.setUserNick(user.getUserNick());
        feedAction.setInsertUserId(user.getUserId());
        feedAction.setUpdateUserId(user.getUserId());
        feedAction.setCanceled(false);
        feedAction.setInsertDate(new Date());
        feedAction.setUpdateDate(new Date());
        feedAction.setFeedActionType(feedActionType);
        return feedAction;
    }

    private static FeedAction createFeedAction(FeedReply feedReply, User user, FeedActionType feedActionType) {
        FeedAction feedAction = new FeedAction();
        feedAction.setFeedId(feedReply.getFeed().getFeedId());
        feedAction.setFeedReplyId(feedReply.getFeedReplyId());
        feedAction.setUserId(user.getUserId());
        feedAction.setUserNick(user.getUserNick());
        feedAction.setInsertUserId(user.getUserId());
        feedAction.setUpdateUserId(user.getUserId());
        feedAction.setCanceled(false);
        feedAction.setInsertDate(new Date());
        feedAction.setUpdateDate(new Date());
        feedAction.setFeedActionType(feedActionType);
        return feedAction;
    }
}
