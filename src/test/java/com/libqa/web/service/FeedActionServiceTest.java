package com.libqa.web.service;

import com.google.common.collect.Lists;
import com.libqa.web.domain.FeedAction;
import com.libqa.web.domain.User;
import com.libqa.web.repository.FeedActionRepository;
import com.libqa.web.service.feed.FeedActionService;
import com.libqa.web.service.feed.actor.FeedLike;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static com.libqa.application.enums.FeedActionType.LIKE;
import static com.libqa.application.enums.FeedThreadType.FEED;
import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class FeedActionServiceTest {
    @Mock
    private FeedActionRepository feedActionRepository;

    @InjectMocks
    private FeedActionService sut = new FeedActionService();

    @Test
    public void 피드의_좋아요_액션을_생성한다() {
        final User user = userFixture();
        final FeedLike feedLike = FeedLike.of(100000, user);

        sut.action(feedLike);

        verify(feedActionRepository).save(any(FeedAction.class));
    }

    @Test
    public void 피드의_좋아요_액션이_재호출되면_액션은_취소된다() {
        final User user = userFixture();
        final FeedLike feedLike = FeedLike.of(100000, user);
        final FeedAction expectedFeedAction = feedLikeFixture(feedLike.getFeedActorId(), user.getUserId());
        given(feedActionRepository.findByFeedActorIdAndUserIdAndIsCanceledFalse(feedLike.getFeedActorId(), user.getUserId()))
                .willReturn(Lists.newArrayList(expectedFeedAction));

        sut.action(feedLike);

        assertThat(expectedFeedAction.isCanceled()).isTrue();
    }

    @Test
    public void 취소된_액션이_존재하면_액션은_다시_생성된다() {
        final User user = userFixture();
        final FeedLike feedLike = FeedLike.of(100000, user);
        given(feedActionRepository.findByFeedActorIdAndUserIdAndIsCanceledFalse(feedLike.getFeedActorId(), user.getUserId()))
                .willReturn(feedLikeFixturesWithCancel(feedLike.getFeedActorId(), user.getUserId()));

        sut.action(feedLike);

        verify(feedActionRepository).save(any(FeedAction.class));
    }

    @Test
    public void 특정_사용자의_피드에_좋아요_액션이_존재한다() {
        final int feedId = 1234;
        final User user = userFixture();
        final FeedLike feedLike = FeedLike.of(feedId, user);
        given(feedActionRepository.findByFeedActorIdAndUserIdAndIsCanceledFalse(feedId, user.getUserId()))
                .willReturn(feedLikeFixtures(feedId, user.getUserId()));

        FeedAction actual = sut.getFeedActionBy(feedLike);

        assertThat(actual.isActed()).isTrue();
    }

    @Test
    public void 취소된_액션은_취하지_않은_액션으로_간주한다() {
        final User user = userFixture();
        final FeedLike feedLike = FeedLike.of(10000, user);

        given(feedActionRepository.findByFeedActorIdAndUserIdAndIsCanceledFalse(feedLike.getFeedActorId(), user.getUserId()))
                .willReturn(feedLikeFixturesWithCancel(feedLike.getFeedActorId(), user.getUserId()));

        FeedAction actual = sut.getFeedActionBy(feedLike);

        assertThat(actual.isActed()).isFalse();
    }

    @Test
    public void 피드_좋아요_액션_카운트를_조회한다() {
        final FeedLike feedLike = FeedLike.of(10000, userFixture());

        sut.countOf(feedLike);

        verify(feedActionRepository).countByFeedActorIdAndFeedThreadTypeAndFeedActionTypeAndIsCanceledFalse(
                eq(feedLike.getFeedActorId()), eq(feedLike.getFeedThreadType()), eq(feedLike.getFeedActionType()));
    }

    private FeedAction feedLikeFixture(int actorId, int userId) {
        FeedAction feedAction = new FeedAction();
        feedAction.setFeedActionId(actorId);
        feedAction.setUserId(userId);

        feedAction.setCanceled(false);
        feedAction.setFeedThreadType(FEED);
        feedAction.setFeedActionType(LIKE);
        return feedAction;
    }

    private List<FeedAction> feedLikeFixtures(int actorId, int userId) {
        FeedAction notCanceled = new FeedAction();
        notCanceled.setFeedActionId(actorId);
        notCanceled.setUserId(userId);

        notCanceled.setCanceled(false);
        notCanceled.setFeedThreadType(FEED);
        notCanceled.setFeedActionType(LIKE);

        FeedAction canceled = new FeedAction();
        canceled.setFeedActionId(actorId);
        canceled.setUserId(userId);
        canceled.setCanceled(true);
        canceled.setFeedThreadType(FEED);
        canceled.setFeedActionType(LIKE);
        return Lists.newArrayList(notCanceled, canceled);
    }

    private List<FeedAction> feedLikeFixturesWithCancel(int actorId, int userId) {
        FeedAction canceled = new FeedAction();
        canceled.setFeedActionId(actorId);
        canceled.setUserId(userId);
        canceled.setCanceled(true);
        canceled.setFeedActionType(LIKE);
        return Lists.newArrayList(canceled);
    }

    private User userFixture() {
        User user = new User();
        user.setUserNick("tester");
        user.setUserId(10000);
        return user;
    }

}