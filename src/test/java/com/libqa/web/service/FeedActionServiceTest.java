package com.libqa.web.service;

import com.google.common.collect.Lists;
import com.libqa.web.domain.FeedAction;
import com.libqa.web.domain.User;
import com.libqa.web.repository.FeedActionRepository;
import com.libqa.web.service.feed.FeedActionService;
import com.libqa.web.service.feed.actor.FeedActionActor;
import com.libqa.web.service.feed.actor.FeedLike;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

@Slf4j
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

        sut.act(feedLike);

        verify(feedActionRepository).save(any(FeedAction.class));
    }

    @Test
    public void 피드의_좋아요_액션이_재호출되면_액션은_취소된다() {
        final User user = userFixture();
        final FeedLike feedLike = FeedLike.of(100000, user);
        final FeedAction expectedFeedAction = feedActionFixture(feedLike);
        given(feedActionRepository.findByFeedActorIdAndUserIdAndIsCanceledFalse(feedLike.getFeedActorId(), user.getUserId()))
                .willReturn(Lists.newArrayList(expectedFeedAction));

        sut.act(feedLike);

        assertThat(expectedFeedAction.isCanceled()).isTrue();
    }

    @Test
    public void 취소된_액션이_존재하면_액션은_다시_생성된다() {
        final User user = userFixture();
        final FeedLike feedLike = FeedLike.of(100000, user);
        given(feedActionRepository.findByFeedActorIdAndUserIdAndIsCanceledFalse(feedLike.getFeedActorId(), user.getUserId()))
                .willReturn(onlyCancelFeedActionsFixture(feedLike));

        sut.act(feedLike);

        verify(feedActionRepository).save(any(FeedAction.class));
    }

    @Test
    public void 특정_사용자의_피드에_좋아요_액션이_존재한다() {
        final int feedThreadId = 1234;
        final User user = userFixture();
        final FeedLike feedLike = FeedLike.of(feedThreadId, user);
        given(feedActionRepository.findByFeedActorIdAndUserIdAndIsCanceledFalse(feedThreadId, user.getUserId()))
                .willReturn(feedActionFixtures(feedLike));

        FeedAction actual = sut.getFeedActionBy(feedLike);

        assertThat(actual.isActed()).isTrue();
    }

    @Test
    public void 취소된_액션은_취하지_않은_액션으로_간주한다() {
        final User user = userFixture();
        final FeedLike feedLike = FeedLike.of(10000, user);

        given(feedActionRepository.findByFeedActorIdAndUserIdAndIsCanceledFalse(feedLike.getFeedActorId(), user.getUserId()))
                .willReturn(onlyCancelFeedActionsFixture(feedLike));

        FeedAction actual = sut.getFeedActionBy(feedLike);

        assertThat(actual.isNotYet()).isTrue();
    }

    @Test
    public void 피드_좋아요_액션_카운트를_조회한다() {
        final FeedLike feedLike = FeedLike.of(10000, userFixture());

        sut.countOf(feedLike);

        verify(feedActionRepository).countByFeedActorIdAndPostTypeAndActionTypeAndIsCanceledFalse(
                eq(feedLike.getFeedActorId()), eq(feedLike.getPostType()), eq(feedLike.getActionType()));
    }


    private FeedAction feedActionFixture(FeedActionActor feedActionActor) {
        FeedAction feedAction = new FeedAction();
        feedAction.setFeedActionId(feedActionActor.getFeedActorId());
        feedAction.setPostType(feedActionActor.getPostType());
        feedAction.setActionType(feedActionActor.getActionType());
        feedAction.setUserId(feedActionActor.getActionUser().getUserId());
        feedAction.setCanceled(false);
        return feedAction;
    }

    private List<FeedAction> feedActionFixtures(FeedActionActor feedActionActor) {
        FeedAction notCanceled = new FeedAction();
        notCanceled.setFeedActionId(feedActionActor.getFeedActorId());
        notCanceled.setPostType(feedActionActor.getPostType());
        notCanceled.setActionType(feedActionActor.getActionType());
        notCanceled.setUserId(feedActionActor.getActionUser().getUserId());
        notCanceled.setCanceled(false);

        FeedAction canceled = new FeedAction();
        canceled.setFeedActionId(feedActionActor.getFeedActorId());
        canceled.setPostType(feedActionActor.getPostType());
        canceled.setActionType(feedActionActor.getActionType());
        canceled.setUserId(feedActionActor.getActionUser().getUserId());
        canceled.setCanceled(true);

        return Lists.newArrayList(notCanceled, canceled);
    }

    private List<FeedAction> onlyCancelFeedActionsFixture(FeedActionActor feedActionActor) {
        FeedAction canceled = new FeedAction();
        canceled.setFeedActionId(feedActionActor.getFeedActorId());
        canceled.setActionType(feedActionActor.getActionType());
        canceled.setUserId(feedActionActor.getActionUser().getUserId());
        canceled.setCanceled(true);
        return Lists.newArrayList(canceled);
    }

    private User userFixture() {
        User user = new User();
        user.setUserNick("tester");
        user.setUserId(10000);
        return user;
    }

}