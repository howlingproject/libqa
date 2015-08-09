package com.libqa.web.service;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.libqa.web.domain.Feed;
import com.libqa.web.domain.FeedAction;
import com.libqa.web.domain.FeedReply;
import com.libqa.web.repository.FeedActionRepository;
import com.libqa.web.repository.FeedReplyRepository;
import com.libqa.web.repository.FeedRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.libqa.application.enums.FeedActionTypeEnum.CLAIM;
import static com.libqa.application.enums.FeedActionTypeEnum.LIKE;

@Slf4j
@Service
public class FeedActionService {
    private static final Predicate<FeedAction> PREDICATE_IS_LIKE = input -> LIKE == input.getFeedActionType();
    private static final Predicate<FeedAction> PREDICATE_IS_CLAIM = input -> CLAIM == input.getFeedActionType();
    
    @Autowired
    private FeedActionRepository feedActionRepository;
    @Autowired
    private FeedRepository feedRepository;
    @Autowired
    private FeedReplyRepository feedReplyRepository;

    public void likeFeed(long feedId) {
        Feed feed = findFeed(feedId);
        FeedAction feedAction = FeedActionFactory.createLike(feed);
        feedActionRepository.save(feedAction);
    }

    public void claimFeed(long feedId) {
        Feed feed = findFeed(feedId);
        FeedAction feedAction = FeedActionFactory.createClaim(feed);
        feedActionRepository.save(feedAction);
    }

    public void likeFeedReply(long feedReplyId) {
        FeedReply feedReply = getFeedReply(feedReplyId);
        FeedAction feedAction = FeedActionFactory.createLike(feedReply);
        feedActionRepository.save(feedAction);
    }

    public void claimFeedReply(long feedReplyId) {
        FeedReply feedReply = getFeedReply(feedReplyId);
        FeedAction feedAction = FeedActionFactory.createClaim(feedReply);
        feedActionRepository.save(feedAction);
    }

    public void cancel(long feedActionId) {
        FeedAction feedAction = feedActionRepository.findOne(feedActionId);
        feedAction.setCanceled(true);
    }

    public boolean hasLikedFeed(long feedId, Integer userId) {
        List<FeedAction> feedActions = feedActionRepository.findByFeedIdAndUserId(feedId, userId);
        return Iterables.tryFind(feedActions, PREDICATE_IS_LIKE).isPresent();
    }
    
    public boolean hasClaimFeed(long feedId, Integer userId) {
        List<FeedAction> feedActions = feedActionRepository.findByFeedIdAndUserId(feedId, userId);
        return Iterables.tryFind(feedActions, PREDICATE_IS_CLAIM).isPresent();
    }

    public boolean hasLikedFeedReply(long feedReplyId, Integer userId) {
        List<FeedAction> feedActions = feedActionRepository.findByFeedReplyIdAndUserId(feedReplyId, userId);
        return Iterables.tryFind(feedActions, PREDICATE_IS_LIKE).isPresent();
    }

    public boolean hasClaimFeedReply(long feedReplyId, Integer userId) {
        List<FeedAction> feedActions = feedActionRepository.findByFeedReplyIdAndUserId(feedReplyId, userId);
        return Iterables.tryFind(feedActions, PREDICATE_IS_CLAIM).isPresent();
    }

    private Feed findFeed(long feedId) {
        return feedRepository.findOne(feedId);
    }

    private FeedReply getFeedReply(long feedReplyId) {
        return feedReplyRepository.findOne(feedReplyId);
    }

}
