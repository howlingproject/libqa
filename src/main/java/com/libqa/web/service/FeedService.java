package com.libqa.web.service;

import com.google.common.collect.Lists;
import com.libqa.application.util.LoggedUser;
import com.libqa.web.domain.Feed;
import com.libqa.web.domain.FeedFile;
import com.libqa.web.repository.FeedFileRepository;
import com.libqa.web.repository.FeedRepository;
import com.libqa.web.view.DisplayFeed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;
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
    private FeedFileRepository feedFileRepository;

    public List<DisplayFeed> search(int startIdx, int endIdx) {
        PageRequest pageRequest = new PageRequest(startIdx, endIdx, new Sort(new Order(Direction.DESC, "feedId")));
        List<DisplayFeed> displayFeeds = Lists.newArrayList();
        List<Feed> feeds = feedRepository.findAll(pageRequest).getContent();
        for (Feed feed : feeds) {
            displayFeeds.add(new DisplayFeed(feed));
        }
        
        
        return displayFeeds;
        
    }

    public void save(Feed feed) {
//        User user = loggedUser.get();
        
        feed.setInsertDate(new Date());
//        feed.setUserNick(user.getUserNick());
//        feed.setUserId(user.getUserId());
//        feed.setInsertUserId(user.getUserId());
        feed.setUserId(1234);
        feed.setUserNick("testerNick");
        feed.setInsertUserId(1234);
        
        saveFeedFiles(feed);
        feedRepository.save(feed);
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
            feedFileRepository.save(each);
        }
    }
}
