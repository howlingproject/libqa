package com.libqa.web.service;

import com.google.common.collect.Lists;
import com.libqa.application.util.LoggedUser;
import com.libqa.web.domain.Feed;
import com.libqa.web.domain.FeedFile;
import com.libqa.web.domain.FeedLikeUser;
import com.libqa.web.domain.FeedReply;
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
    private FeedLikeUserService feedLikeUserService;

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
//        User user = loggedUser.get(); // TODO 개발 완료후 로그인 기능 추가할 예정.

//        feed.setUserNick(user.getUserNick());
//        feed.setUserId(user.getUserId());
//        feed.setInsertUserId(user.getUserId());
        feed.setUserId(1234);
        feed.setUserNick("testerNick");
        feed.setInsertUserId(1234);
        feed.setInsertDate(new Date());
        feedRepository.save(feed);
        saveFeedFiles(feed);
    }

    @Transactional
    public void delete(Long feedId) {
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

    @Transactional
    public int likeOrUnlike(long feedId, Integer userId) {
        Feed feed = feedRepository.findByFeedIdAndUserId(feedId, userId);
        FeedLikeUser recentlyFeedLikeUser = feedLikeUserService.getRecentlyFeedLikeUserBy(feed);

        boolean liked = likeOrUnlike(recentlyFeedLikeUser);
        feed.setLikeCount(liked ? feed.getLikeCount() + 1 : feed.getLikeCount() - 1);
        return feed.getLikeCount();
    }

    private boolean likeOrUnlike(FeedLikeUser feedLikeUser) {
        boolean likable = feedLikeUserService.isLikable(feedLikeUser);
        if (likable) {
            feedLikeUserService.like(feedLikeUser);
        } else {
            feedLikeUserService.disLike(feedLikeUser);
        }
        return likable;
    }

}
