package com.libqa.web.service.feed;


import com.libqa.web.repository.FeedRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.domain.PageRequest;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class FeedServiceTest {

    @Mock
    private FeedRepository feedRepository;

    @InjectMocks
    private FeedService sut;

    @Test
    public void lastFeedId가_null이면_해당_사용자의_피드목록을_제공한다() {
        final int userId = 10000;
        final Integer lastFeedId = null;

        sut.searchByUserId(userId, lastFeedId);

        verify(feedRepository, never()).findByUserIdAndFeedIdLessThanAndIsDeletedFalse(eq(userId), eq(lastFeedId), any(PageRequest.class));
        verify(feedRepository).findByUserIdAndIsDeletedFalse(eq(userId), any(PageRequest.class));
    }

    @Test
    public void lastFeedId가_null이_아니면_lastFeedId보다_작은_해당_사용자의_피드목록을_제공한다() {
        final int userId = 10000;
        final Integer lastFeedId = 10;

        sut.searchByUserId(userId, lastFeedId);

        verify(feedRepository).findByUserIdAndFeedIdLessThanAndIsDeletedFalse(eq(userId), eq(lastFeedId), any(PageRequest.class));
        verify(feedRepository, never()).findByUserIdAndIsDeletedFalse(eq(userId), any(PageRequest.class));
    }
}