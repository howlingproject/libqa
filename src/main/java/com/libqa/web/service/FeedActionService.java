package com.libqa.web.service;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.libqa.application.enums.FeedActionTypeEnum;
import com.libqa.web.domain.Feed;
import com.libqa.web.domain.FeedAction;
import com.libqa.web.domain.FeedReply;
import com.libqa.web.domain.User;
import com.libqa.web.repository.FeedActionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.libqa.application.enums.FeedActionTypeEnum.*;

@Slf4j
@Service
public class FeedActionService {
    private static Predicate<FeedAction> TO_NORMAL(final FeedActionTypeEnum feedActionType) {
        return input -> input.isNotCanceled() && input.getFeedActionType() == feedActionType;
    }

    @Autowired
    private FeedActionRepository feedActionRepository;

    public FeedAction like(Feed feed, User user) {
        FeedAction feedAction = FeedActionFactory.createLike(feed, user);
        feedActionRepository.save(feedAction);
        return feedAction;
    }

    public FeedAction claim(Feed feed, User user) {
        FeedAction feedAction = FeedActionFactory.createClaim(feed, user);
        feedActionRepository.save(feedAction);
        return feedAction;
    }

    public FeedAction like(FeedReply feedReply, User user) {
        FeedAction feedAction = FeedActionFactory.createLike(feedReply, user);
        feedActionRepository.save(feedAction);
        return feedAction;
    }

    public FeedAction claim(FeedReply feedReply, User user) {
        FeedAction feedAction = FeedActionFactory.createClaim(feedReply, user);
        feedActionRepository.save(feedAction);
        return feedAction;
    }

    public FeedAction getLiked(Feed feed, User user) {
        return findFeedAction(feed, user, FEED_LIKE);
    }

    public FeedAction getClaimed(Feed feed, User user) {
        return findFeedAction(feed, user, FEED_CLAIM);
    }

    public FeedAction getLiked(FeedReply feedReply, User user) {
        return findFeedAction(feedReply, user, FEED_REPLY_LIKE);
    }

    public FeedAction getClaimed(FeedReply feedReply, User user) {
        return findFeedAction(feedReply, user, FEED_REPLY_CLAIM);
    }

    public boolean hasLike(Feed feed, User user) {
        return getLiked(feed, user) != null;
    }

    public boolean hasClaim(Feed feed, User user) {
        return getClaimed(feed, user) != null;
    }

    public boolean hasLike(FeedReply feedReply, User user) {
        return getLiked(feedReply, user) != null;
    }

    public boolean hasClaim(FeedReply feedReply, User user) {
        return getClaimed(feedReply, user) != null;
    }

    private FeedAction findFeedAction(Feed feed, User user, FeedActionTypeEnum feedActionType) {
        List<FeedAction> feedActions = feedActionRepository.findByFeedIdAndUserId(feed.getFeedId(), user.getUserId());
        return Iterables.tryFind(feedActions, TO_NORMAL(feedActionType)).orNull();
    }

    private FeedAction findFeedAction(FeedReply feedReply, User user, FeedActionTypeEnum feedActionType) {
        List<FeedAction> feedActions = feedActionRepository.findByFeedReplyIdAndUserId(feedReply.getFeedReplyId(), user.getUserId());
        return Iterables.tryFind(feedActions, TO_NORMAL(feedActionType)).orNull();
    }

    public Integer getLikeCount(Feed feed) {
        return feedActionRepository.countByFeedIdAndFeedActionTypeAndIsCanceled(feed.getFeedId(), FEED_LIKE, false);
    }

    public Integer getClaimCount(Feed feed) {
        return feedActionRepository.countByFeedIdAndFeedActionTypeAndIsCanceled(feed.getFeedId(), FEED_CLAIM, false);
    }
    
    public Integer getLikeCount(FeedReply feedReply) {
        return feedActionRepository.countByFeedReplyIdAndFeedActionTypeAndIsCanceled(feedReply.getFeedReplyId(), FEED_REPLY_LIKE, false);
    }

    public Integer getClaimCount(FeedReply feedReply) {
        return feedActionRepository.countByFeedReplyIdAndFeedActionTypeAndIsCanceled(feedReply.getFeedReplyId(), FEED_REPLY_CLAIM, false);
    }
}
