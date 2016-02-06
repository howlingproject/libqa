package com.libqa.web.service.feed;


import com.libqa.web.domain.User;
import com.libqa.web.repository.FeedThreadRepository;
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
public class FeedThreadServiceTest {

    @Mock
    private FeedThreadRepository feedThreadRepository;

    @InjectMocks
    private FeedThreadService sut;

    @Test
    public void lastFeedThreadId가_null이면_해당_사용자의_피드목록을_제공한다() {
        final User user = userFixture();
        final Integer lastFeedThreadId = null;

        sut.searchRecentlyFeedsByUserWithlastFeedThreadId(user, lastFeedThreadId);

        verify(feedThreadRepository, never()).findByUserIdAndFeedThreadIdLessThanAndIsDeletedFalse(eq(user.getUserId()), eq(lastFeedThreadId), any(PageRequest.class));
        verify(feedThreadRepository).findByUserIdAndIsDeletedFalse(eq(user.getUserId()), any(PageRequest.class));
    }

    @Test
    public void lastFeedThreadId가_null이_아니면_lastFeedThreadId보다_작은_해당_사용자의_피드목록을_제공한다() {
        final User user = userFixture();
        final Integer lastFeedThreadId = 10;

        sut.searchRecentlyFeedsByUserWithlastFeedThreadId(user, lastFeedThreadId);

        verify(feedThreadRepository).findByUserIdAndFeedThreadIdLessThanAndIsDeletedFalse(eq(user.getUserId()), eq(lastFeedThreadId), any(PageRequest.class));
        verify(feedThreadRepository, never()).findByUserIdAndIsDeletedFalse(eq(user.getUserId()), any(PageRequest.class));
    }

    private User userFixture() {
        User user = new User();
        user.setUserId(12345);
        return user;
    }
}