package com.libqa.web.service;

import com.libqa.web.domain.*;
import com.libqa.web.repository.FeedFileRepository;
import com.libqa.web.repository.FeedReplyRepository;
import com.libqa.web.repository.FeedRepository;
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
    private FeedRepository feedRepository;
    @Autowired
    private FeedFileService feedFileService;
    @Autowired
    private FeedReplyRepository feedReplyRepository;
    @Autowired
    private FeedFileRepository feedFileRepository;
    @Autowired
    private FeedActionService feedActionService;

    public List<Feed> search(int startIdx, int endIdx) {
        PageRequest pageRequest = new PageRequest(startIdx, endIdx, new Sort(new Order(DESC, "feedId")));
        return feedRepository.findByIsDeleted(false, pageRequest);
    }

    @Transactional
    public void save(Feed feed, User user) {
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

    @Transactional
    public Feed like(Integer feedId, User user) {
        Feed feed = feedRepository.findOne(feedId);
        FeedAction likedFeedAction = feedActionService.getLiked(feed, user);
        if (likedFeedAction == null) {
            feedActionService.like(feed, user);
        } else {
            likedFeedAction.cancel();
        }
        feed.setLikeCount(feedActionService.getLikeCount(feed));
        return feed;
    }

    @Transactional
    public Feed claim(Integer feedId, User user) {
        Feed feed = feedRepository.findOne(feedId);
        FeedAction claimedFeedAction = feedActionService.getClaimed(feed, user);
        if (claimedFeedAction == null) {
            feedActionService.claim(feed, user);
        } else {
            claimedFeedAction.cancel();
        }
        feed.setClaimCount(feedActionService.getClaimCount(feed));
        return feed;
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
