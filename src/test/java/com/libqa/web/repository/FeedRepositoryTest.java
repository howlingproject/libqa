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
        final int lastFeedId = 0;
        final PageRequest pageRequest = PageUtil.sortPageable(new Sort(DESC, "feedId"));
        List<Feed> feeds = repository.findByFeedIdLessThanAndIsDeletedFalse(lastFeedId, pageRequest);
        System.out.println(feeds);
    }

    @Test
    public void findByIsDeletedFalse() {
        final PageRequest pageRequest = new PageRequest(0, 5, new Sort(new Sort.Order(DESC, "feedId")));
        List<Feed> feeds = repository.findByIsDeletedFalse(pageRequest);
        System.out.println(feeds);
    }

    @Test
    public void countFeedRepliesByFeedId() {
        Integer count = repository.countFeedRepliesByFeedId(-1);
        System.out.println(count);
    }

    @Test
    public void findByUserIdAndIsDeletedFalse() {
        final int userId = 100000;
        final PageRequest pageRequest = PageUtil.sortPageable(new Sort(DESC, "feedId"));
        List<Feed> feeds = repository.findByUserIdAndIsDeletedFalse(userId, pageRequest);
        System.out.println(feeds);
    }


    @Test
    public void findByUserIdAndFeedIdLessThanAndIsDeletedFalse() {
        final int userId = 100000;
        final int lastFeedId = 1000;
        final PageRequest pageRequest = PageUtil.sortPageable(new Sort(DESC, "feedId"));
        List<Feed> feeds = repository.findByUserIdAndFeedIdLessThanAndIsDeletedFalse(userId, lastFeedId, pageRequest);
        System.out.println(feeds);
    }
}
