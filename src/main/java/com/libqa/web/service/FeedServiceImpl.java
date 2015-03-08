package com.libqa.web.service;

import com.libqa.domain.Feed;
import com.libqa.repository.FeedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedServiceImpl implements FeedService {
    @Autowired
    private FeedRepository feedRepository;

    @Override
    public void insert(Feed feed) {
        feedRepository.save(feed);
    }

    @Override
    public List<Feed> getAll() {
        Sort sort = new Sort(new Order(Direction.DESC, "feed_id"));
        Page<Feed> page = feedRepository.findAll(new PageRequest(0, 10, sort));
        return page.getContent();
    }
}
