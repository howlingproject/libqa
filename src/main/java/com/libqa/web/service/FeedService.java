package com.libqa.web.service;

import com.libqa.domain.Feed;
import org.springframework.data.domain.Page;

import java.util.List;

public interface FeedService {
    public void insert(Feed feed);
    public List<Feed> getAll();
}
