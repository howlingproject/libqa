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
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class FeedThreadServiceTest {

    @Mock
    private FeedThreadRepository feedThreadRepository;

    @InjectMocks
    private FeedThreadService sut;

    @Test
    public void lastId_보다_작은_사용자_피드목록을_제공한다() {
        final User user = userFixture();
        final Integer lastId = 10;

        sut.getRecentlyFeedThreadsByUserLessThanLastId(user, lastId);

        verify(feedThreadRepository).findByUserIdAndFeedThreadIdLessThanAndIsDeletedFalse(eq(user.getUserId()), eq(lastId), any(PageRequest.class));
    }

    private User userFixture() {
        User user = new User();
        user.setUserId(12345);
        return user;
    }
}