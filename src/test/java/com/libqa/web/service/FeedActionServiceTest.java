package com.libqa.web.service;

import com.google.common.collect.Lists;
import com.libqa.application.enums.FeedActionType;
import com.libqa.web.domain.FeedAction;
import com.libqa.web.repository.FeedActionRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static com.libqa.application.enums.FeedActionType.FEED_LIKE;
import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class FeedActionServiceTest {
    @Mock
    private FeedActionRepository feedActionRepository;

    @InjectMocks
    private FeedActionService sut = new FeedActionService();

    @Test
    public void 특정_사용자의_피드의_좋아요_액션을_생성한다() {
        final int feedId = 1234;
        final int userId = 1234;
        final String userNick = "tester";
        final FeedActionType feedActionType = FEED_LIKE;

        sut.create(feedId, userId, userNick, feedActionType);

        verify(feedActionRepository).save(any(FeedAction.class));
    }

    @Test
    public void 특정_사용자의_피드에_좋아요_액션이_존재한다() {
        final int feedId = 1234;
        final int userId = 1234;
        final FeedActionType feedActionType = FEED_LIKE;
        given(feedActionRepository.findByFeedActorIdAndUserId(feedId, userId)).willReturn(feedLikeFixtures());

        FeedAction actual = sut.getFeedAction(feedId, userId, feedActionType);

        assertThat(actual.isActed()).isTrue();
    }

    @Test
    public void 특정_사용자의_피드에_어떤_액션도_존재하지_않는다() {
        final int feedId = 1234;
        final int userId = 1234;
        final FeedActionType feedActionType = FEED_LIKE;
        given(feedActionRepository.findByFeedActorIdAndUserId(feedId, userId)).willReturn(feedLikeFixturesOnlyCanceled());

        FeedAction actual = sut.getFeedAction(feedId, userId, feedActionType);

        assertThat(actual.isNotYet()).isTrue();
    }

    @Test
    public void 피드_좋아요_액션_카운트를_조회한다() {
        final int feedId = 1234;
        final FeedActionType feedActionType = FEED_LIKE;

        sut.getCount(feedId, feedActionType);

        verify(feedActionRepository).countByFeedActorIdAndFeedActionTypeAndIsCanceledFalse(anyInt(), any(FeedActionType.class));

    }

    private List<FeedAction> feedLikeFixtures() {
        FeedAction normal = new FeedAction();
        normal.setCanceled(false);
        normal.setFeedActionType(FEED_LIKE);

        FeedAction canceled = new FeedAction();
        canceled.setCanceled(true);
        canceled.setFeedActionType(FEED_LIKE);
        return Lists.newArrayList(normal, canceled);
    }


    private List<FeedAction> feedLikeFixturesOnlyCanceled() {
        FeedAction canceled = new FeedAction();
        canceled.setCanceled(true);
        canceled.setFeedActionType(FEED_LIKE);
        return Lists.newArrayList(canceled);
    }

}