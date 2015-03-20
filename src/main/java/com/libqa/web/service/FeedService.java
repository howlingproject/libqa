package com.libqa.web.service;

import com.libqa.web.domain.Feed;

import java.util.List;

public interface FeedService {
    public void insert(Feed feed);
    public List<Feed> getAll();
}
