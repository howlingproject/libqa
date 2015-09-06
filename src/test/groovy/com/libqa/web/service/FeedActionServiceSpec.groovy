package com.libqa.web.service

import com.libqa.application.enums.FeedActionTypeEnum
import com.libqa.web.domain.Feed
import com.libqa.web.domain.FeedAction
import com.libqa.web.domain.FeedReply
import com.libqa.web.domain.User
import com.libqa.web.repository.FeedActionRepository
import spock.lang.Specification

import static com.libqa.application.enums.FeedActionTypeEnum.*

class FeedActionServiceSpec extends Specification {
    FeedActionRepository feedActionRepository
    FeedActionService sut

    def setup() {
        feedActionRepository = Mock(FeedActionRepository)
        sut = new FeedActionService(feedActionRepository: feedActionRepository)
    }

    def "피드에 좋아요를 할 수 있다."() {
        given:
        def feed = feedFixture()
        def user = userFixture()
        when:
        sut.like(feed, user);
        then:
        1 * feedActionRepository.save(_)
    }

    def "피드에 신고하기를 할 수 있다."() {
        given:
        def feed = feedFixture()
        def user = userFixture()
        when:
        sut.claim(feed, user);
        then:
        1 * feedActionRepository.save(_)
    }

    def "피드의 댓글에 좋아요를 할 수 있다."() {
        given:
        def feedReply = feedReplyFixture()
        def user = userFixture()
        when:
        sut.like(feedReply, user);
        then:
        1 * feedActionRepository.save(_)
    }

    def "피드의 댓글에 신고하기를 할 수 있다."() {
        given:
        def feedReply = feedReplyFixture()
        def user = userFixture()
        when:
        sut.claim(feedReply, user);
        then:
        1 * feedActionRepository.save(_)
    }

    def "좋아요/신고하기를 취소할 수 있다."() {
        given:
        def feedActionId = 1
        def feedAction = feedActionFixture(FEED_LIKE)
        feedActionRepository.findOne(feedActionId) >> feedAction
        when:
        sut.cancel(feedActionId);
        then:
        feedAction.canceled == true
    }

    def "특정 FEED에 자신이 좋아요를 한 적이 있다."() {
        given:
        def feedId = 1
        def userId = 1
        feedActionRepository.findByFeedIdAndUserId(feedId, userId) >> [
                feedActionFixture(FEED_LIKE)
        ]
        when:
        def response = sut.hasLikedFeed(feedId, userId)
        then:
        response == true
    }

    def "특정 FEED에 자신이 좋아요를 한 적이 없다."() {
        given:
        def feedId = 1
        def userId = 1
        feedActionRepository.findByFeedIdAndUserId(feedId, userId) >> [
                feedActionFixture(FEED_REPLY_CLAIM)
        ]
        when:
        def response = sut.hasLikedFeed(feedId, userId)
        then:
        response == false
    }

    def "특정 FEED에 자신이 신고하기를 한 적이 있다."() {
        given:
        def feedId = 1
        def userId = 1
        feedActionRepository.findByFeedIdAndUserId(feedId, userId) >> [
                feedActionFixture(FEED_CLAIM)
        ]
        when:
        def response = sut.hasClaimFeed(feedId, userId)
        then:
        response == true
    }

    def "특정 FEED에 자신이 신고하기를 한 적이 없다."() {
        given:
        def feedId = 1
        def userId = 1
        feedActionRepository.findByFeedIdAndUserId(feedId, userId) >> []
        when:
        def response = sut.hasClaimFeed(feedId, userId)
        then:
        response == false
    }

    def "특정 FEED의 댓글에 자신이 좋아요를 한 적이 있다."() {
        given:
        def feedId = 1
        def userId = 1
        feedActionRepository.findByFeedReplyIdAndUserId(feedId, userId) >> [
                feedActionFixture(FEED_REPLY_LIKE)
        ]
        when:
        def response = sut.hasLikedFeedReply(feedId, userId)
        then:
        response == true
    }

    def "특정 FEED의 댓글에 자신이 좋아요를 한 적이 없다."() {
        given:
        def feedId = 1
        def userId = 1
        feedActionRepository.findByFeedReplyIdAndUserId(feedId, userId) >> [
                feedActionFixture(FEED_LIKE),
                feedActionFixture(FEED_REPLY_CLAIM)
        ]
        when:
        def response = sut.hasLikedFeedReply(feedId, userId)
        then:
        response == false
    }

    def "특정 FEED의 댓글에 자신이 신고하기를 한 적이 있다."() {
        given:
        def feedId = 1
        def userId = 1
        feedActionRepository.findByFeedReplyIdAndUserId(feedId, userId) >> [
                feedActionFixture(FEED_REPLY_CLAIM)
        ]
        when:
        def response = sut.hasClaimFeedReply(feedId, userId)
        then:
        response == true
    }

    def "특정 FEED의 댓글에 자신이 신고하기를 한 적이 없다."() {
        given:
        def feedId = 1
        def userId = 1
        feedActionRepository.findByFeedReplyIdAndUserId(feedId, userId) >> []
        when:
        def response = sut.hasClaimFeedReply(feedId, userId)
        then:
        response == false
    }

    def feedFixture() {
        def feed = new Feed()
        feed.feedId = 1
        return feed
    }

    def userFixture() {
        def user = new User()
        user.userId = 1234
        user.userEmail = "test@test.com"
        return user
    }

    def feedReplyFixture() {
        def feedReply = new FeedReply()
        feedReply.feed = feedFixture()
        feedReply.feedReplyId = 1
        return feedReply
    }

    def feedActionFixture(FeedActionTypeEnum feedActionType) {
        def feedAction = new FeedAction()
        feedAction.feedActionId = 1
        feedAction.feedActionType = feedActionType
        return feedAction
    }
}
