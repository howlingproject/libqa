package com.libqa.web.service;

import com.libqa.web.domain.Feed;
import com.libqa.web.domain.FeedFile;
import com.libqa.web.domain.User;
import com.libqa.web.repository.FeedFileRepository;
import com.libqa.web.repository.FeedRepository;
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
    private UserService userService;
    @Autowired
    private FeedRepository feedRepository;
    @Autowired
    private FeedFileRepository feedFileRepository;

    public List<Feed> search(int startIdx, int endIdx) {
        PageRequest pageRequest = new PageRequest(startIdx, endIdx, new Sort(new Order(Direction.DESC, "feedId")));
        return feedRepository.findAll(pageRequest).getContent();
    }

    public void save(Feed feed) {
        User user = userService.findByAuthentication();
        
        feed.setInsertDate(new Date());
        feed.setUserNick(user.getUserNick());
        feed.setUserId(user.getUserId());
        feed.setInsertUserId(user.getUserId());
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
