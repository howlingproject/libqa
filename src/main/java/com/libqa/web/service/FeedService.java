package com.libqa.web.service;

import com.google.common.collect.Lists;
import com.libqa.application.util.LoggedUser;
import com.libqa.web.domain.*;
import com.libqa.web.repository.FeedFileRepository;
import com.libqa.web.repository.FeedReplyRepository;
import com.libqa.web.repository.FeedRepository;
import com.libqa.web.view.DisplayFeed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

import static org.springframework.data.domain.Sort.Direction.DESC;

@Slf4j
@Service
public class FeedService {

    @Autowired
    private LoggedUser loggedUser;
    @Autowired
    private FeedRepository feedRepository;
    @Autowired
    private FeedFileService feedFileService;
    @Autowired
    private FeedReplyRepository feedReplyRepository;
    @Autowired
    private FeedFileRepository feedFileRepository;
    @Autowired
    private FeedActionService feedActionService;

    public List<DisplayFeed> search(int startIdx, int endIdx) {
        List<DisplayFeed> displayFeeds = Lists.newArrayList();
        PageRequest pageRequest = new PageRequest(startIdx, endIdx, new Sort(new Order(DESC, "feedId")));
        List<Feed> feeds = feedRepository.findByIsDeleted(false, pageRequest);
        for (Feed feed : feeds) {
            displayFeeds.add(new DisplayFeed(feed));
        }
        return displayFeeds;
    }

    @Transactional
    public void save(Feed feed) {
        User user = loggedUser.getDummyUser(); // TODO fix to realuser
        feed.setUserNick(user.getUserNick());
        feed.setUserId(user.getUserId());
        feed.setInsertUserId(user.getUserId());
        feed.setInsertDate(new Date());
        feedRepository.save(feed);
        saveFeedFiles(feed);
    }

    @Transactional
    public void delete(Integer feedId) {
        Feed feed = feedRepository.findOne(feedId);
        feed.setDeleted(true);

        List<FeedReply> feedReplies = feedReplyRepository.findByFeedFeedId(feedId);
        for (FeedReply each : feedReplies) {
            each.setDeleted(true);
        }

        List<FeedFile> feedFiles = feedFileRepository.findByFeedFeedId(feedId);
        for (FeedFile each : feedFiles) {
            each.setDeleted(true);
        }
    }

    public Integer getLikeCount(Integer feedId) {
        Feed feed = feedRepository.findOne(feedId);
        return feed.getLikeCount();
    }

    public Integer getClaimCount(Integer feedId) {
        Feed feed = feedRepository.findOne(feedId);
        return feed.getClaimCount();
    }    
    
    @Transactional
    public FeedAction like(Integer feedId) {
        User user = loggedUser.getDummyUser(); // TODO fix to realuser
        Feed feed = feedRepository.findOne(feedId);
        FeedAction likedFeedAction = feedActionService.getLiked(feed, user);
        if (likedFeedAction == null) {
            likedFeedAction = feedActionService.like(feed, user);
            feed.increaseLikeCount();
        } else {
            likedFeedAction.cancel();
            feed.decreaseLikeCount();
        }
        return likedFeedAction;
    }

    @Transactional
    public FeedAction claim(Integer feedId) {
        User user = loggedUser.getDummyUser(); // TODO fix to realuser
        Feed feed = feedRepository.findOne(feedId);
        FeedAction claimedFeedAction = feedActionService.getClaimed(feed, user);
        if (claimedFeedAction == null) {
            claimedFeedAction = feedActionService.claim(feed, user);
            feed.increaseClaimCount();
        } else {
            claimedFeedAction.cancel();
            feed.decreaseClaimCount();
        }
        return claimedFeedAction;
    }
    
    private void saveFeedFiles(Feed feed) {
        if (CollectionUtils.isEmpty(feed.getFeedFiles())) {
            return;
        }

        for (FeedFile each : feed.getFeedFiles()) {
            each.setUserNick(feed.getUserNick());
            each.setUserId(feed.getUserId());
            each.setInsertUserId(feed.getInsertUserId());
            each.setInsertDate(new Date());
            each.setFeed(feed);
            feedFileService.save(each);
        }
    }
}
