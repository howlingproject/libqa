package com.libqa.web.service;

import com.libqa.web.domain.Feed;
import com.libqa.web.repository.FeedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedService  {

    @Autowired
    private FeedRepository feedRepository;

    public List<Feed> search(int startIdx, int endIdx) {
        PageRequest pageRequest = new PageRequest(startIdx, endIdx, new Sort(new Order(Direction.DESC, "feedId")));
        return feedRepository.findAll(pageRequest).getContent();
    }

    public void save(Feed feed) {
        feedRepository.save(feed);
    }
}
