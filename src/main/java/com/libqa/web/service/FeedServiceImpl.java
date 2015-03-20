package com.libqa.web.service;

import com.libqa.application.util.PageUtil;
import com.libqa.domain.Feed;
import com.libqa.repository.FeedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
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
        Sort sort = PageUtil.sortId("DESC", "seed_id");
        Page<Feed> page = feedRepository.findAll(PageUtil.sortPageable(0, 10, sort));
        return page.getContent();
    }

}
