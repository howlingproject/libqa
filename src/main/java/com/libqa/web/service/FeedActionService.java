package com.libqa.web.service;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.libqa.web.domain.Feed;
import com.libqa.web.domain.FeedAction;
import com.libqa.web.domain.FeedReply;
import com.libqa.web.domain.User;
import com.libqa.web.repository.FeedActionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.libqa.application.enums.FeedActionTypeEnum.CLAIM;
import static com.libqa.application.enums.FeedActionTypeEnum.LIKE;

@Slf4j
@Service
public class FeedActionService {
    private static final Predicate<FeedAction> PREDICATE_IS_LIKE = input -> LIKE == input.getFeedActionType() && input.isNotCanceled();
    private static final Predicate<FeedAction> PREDICATE_IS_CLAIM = input -> CLAIM == input.getFeedActionType() && input.isNotCanceled();

    @Autowired
    private FeedActionRepository feedActionRepository;

    public FeedAction like(Feed feed, User user) {
        FeedAction feedAction = FeedActionFactory.createLike(feed, user);
        feedActionRepository.save(feedAction);
        return feedAction;
    }

    public FeedAction like(FeedReply feedReply, User user) {
        FeedAction feedAction = FeedActionFactory.createLike(feedReply, user);
        feedActionRepository.save(feedAction);
        return feedAction;
    }
    
    public FeedAction claim(Feed feed, User user) {
        FeedAction feedAction = FeedActionFactory.createClaim(feed, user);
        feedActionRepository.save(feedAction);
        return feedAction;
    }

    public FeedAction claim(FeedReply feedReply, User user) {
        FeedAction feedAction = FeedActionFactory.createClaim(feedReply, user);
        feedActionRepository.save(feedAction);
        return feedAction;
    }

    public boolean hasLikedFeed(Integer feedId, Integer userId) {
        List<FeedAction> feedActions = feedActionRepository.findByFeedIdAndUserId(feedId, userId);
        return Iterables.tryFind(feedActions, PREDICATE_IS_LIKE).isPresent();
    }

    public boolean hasClaimFeed(Integer feedId, Integer userId) {
        List<FeedAction> feedActions = feedActionRepository.findByFeedIdAndUserId(feedId, userId);
        return Iterables.tryFind(feedActions, PREDICATE_IS_CLAIM).isPresent();
    }

    public boolean hasLikedFeedReply(Integer feedReplyId, Integer userId) {
        List<FeedAction> feedActions = feedActionRepository.findByFeedReplyIdAndUserId(feedReplyId, userId);
        return Iterables.tryFind(feedActions, PREDICATE_IS_LIKE).isPresent();
    }

    public boolean hasClaimFeedReply(Integer feedReplyId, Integer userId) {
        List<FeedAction> feedActions = feedActionRepository.findByFeedReplyIdAndUserId(feedReplyId, userId);
        return Iterables.tryFind(feedActions, PREDICATE_IS_CLAIM).isPresent();
    }
    
    public void cancel(Integer feedActionId) {
        FeedAction feedAction = feedActionRepository.findOne(feedActionId);
        feedAction.setCanceled(true);
    }

    public FeedAction getLiked(Feed feed, User user) {
        List<FeedAction> feedActions = feedActionRepository.findByFeedIdAndUserId(feed.getFeedId(), user.getUserId());
        return Iterables.tryFind(feedActions, PREDICATE_IS_LIKE).orNull();
    }

    public FeedAction getClaimed(Feed feed, User user) {
        List<FeedAction> feedActions = feedActionRepository.findByFeedIdAndUserId(feed.getFeedId(), user.getUserId());
        return Iterables.tryFind(feedActions, PREDICATE_IS_CLAIM).orNull();
    }

    public FeedAction getLiked(FeedReply feedReply, User user) {
        List<FeedAction> feedActions = feedActionRepository.findByFeedReplyIdAndUserId(feedReply.getFeedReplyId(), user.getUserId());
        return Iterables.tryFind(feedActions, PREDICATE_IS_LIKE).orNull();
    }

    public FeedAction getClaimed(FeedReply feedReply, User user) {
        List<FeedAction> feedActions = feedActionRepository.findByFeedReplyIdAndUserId(feedReply.getFeedReplyId(), user.getUserId());
        return Iterables.tryFind(feedActions, PREDICATE_IS_CLAIM).orNull();
    }
}
