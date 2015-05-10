package com.libqa.web.service;

import com.google.common.collect.Lists;
import com.libqa.application.util.LoggedUser;
import com.libqa.web.domain.Feed;
import com.libqa.web.domain.FeedFile;
import com.libqa.web.repository.FeedRepository;
import com.libqa.web.view.DisplayFeed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

@Service
public class FeedService {

    @Autowired
    private LoggedUser loggedUser;
    @Autowired
    private FeedRepository feedRepository;
    @Autowired
    private FeedReplyService feedReplyService;
    @Autowired
    private FeedFileService feedFileService;

    public List<DisplayFeed> search(int startIdx, int endIdx) {
        List<DisplayFeed> displayFeeds = Lists.newArrayList();
        PageRequest pageRequest = new PageRequest(startIdx, endIdx, new Sort(new Order(Direction.DESC, "feedId")));
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
        saveFeedFiles(feed);
        feedRepository.save(feed);
    }

    public void delete(Long feedId) {
        Feed feed = feedRepository.findOne(feedId);
        if (feed != null) {
            feed.setDeleted(true);
            feedRepository.save(feed);
            feedReplyService.deleteByFeedId(feedId);
            feedFileService.deleteByFeedId(feedId);
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
            feedFileService.save(each);
        }
    }

}
