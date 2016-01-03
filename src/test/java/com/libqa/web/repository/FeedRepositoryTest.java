package com.libqa.web.repository;

import com.libqa.application.util.PageUtil;
import com.libqa.testsupport.LibqaRepositoryTest;
import com.libqa.web.domain.Feed;
import org.junit.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;

import static junit.framework.Assert.assertNull;
import static org.springframework.data.domain.Sort.Direction.DESC;

public class FeedRepositoryTest extends LibqaRepositoryTest<FeedRepository> {

    @Test
    public void findOne() {
        Feed actual = repository.findOne(-1);
        assertNull(actual);
    }

    @Test
    public void findByFeedIdLessThanAndIsDeletedFalse() {
        int lastFeedId = 0;
        PageRequest pageRequest = PageUtil.sortPageable(new Sort(DESC, "feedId"));
        List<Feed> feeds = repository.findByFeedIdLessThanAndIsDeletedFalse(lastFeedId, pageRequest);
        System.out.println(feeds);
    }

    @Test
    public void findByIsDeletedFalse() {
        PageRequest pageRequest = new PageRequest(0, 5, new Sort(new Sort.Order(DESC, "feedId")));
        List<Feed> feeds = repository.findByIsDeletedFalse(pageRequest);
        System.out.println(feeds);
    }

    @Test
    public void countFeedRepliesByFeedId() {
        Integer count = repository.countFeedRepliesByFeedId(-1);
        System.out.println(count);
    }

}
