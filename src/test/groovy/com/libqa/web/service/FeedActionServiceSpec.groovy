package com.libqa.web.service

import com.libqa.web.domain.Feed
import com.libqa.web.domain.FeedAction
import com.libqa.web.domain.FeedReply
import com.libqa.web.repository.FeedActionRepository
import com.libqa.web.repository.FeedReplyRepository
import com.libqa.web.repository.FeedRepository
import spock.lang.Specification

import static com.libqa.application.enums.FeedActionTypeEnum.CLAIM
import static com.libqa.application.enums.FeedActionTypeEnum.LIKE

class FeedActionServiceSpec extends Specification {
    FeedRepository feedRepository
    FeedActionRepository feedActionRepository
    FeedReplyRepository feedReplyRepository
    FeedActionService sut

    def setup() {
        feedRepository = Mock()
        feedReplyRepository = Mock()
        feedActionRepository = Mock()
        sut = new FeedActionService(
            feedRepository: feedRepository,
            feedReplyRepository: feedReplyRepository,
            feedActionRepository: feedActionRepository
        )
    }
    
    def "피드에 좋아요를 할 수 있다."() {
        given:
        def feedId = 1
        def feed = feedFixture()
        feedRepository.findOne(feedId) >> feed
        when:
        sut.likeFeed(feedId);
        then:
        1 * feedActionRepository.save(_)
    }

    def "피드에 신고하기를 할 수 있다."() {
        given:
        def feedId = 1
        feedRepository.findOne(feedId) >> feedFixture()
        when:
        sut.claimFeed(feedId);
        then:
        1 * feedActionRepository.save(_)
    }

    def "피드의 댓글에 좋아요를 할 수 있다."() {
        given:
        def feedReplyId = 10                                 
        feedReplyRepository.findOne(feedReplyId) >> feedReplyFixture()
        when:
        sut.likeFeedReply(feedReplyId);
        then:
        1 * feedActionRepository.save(_)
    }

    def "피드의 댓글에 신고하기를 할 수 있다."() {
        given:
        def feedReplyId = 10                                 
        feedReplyRepository.findOne(feedReplyId) >> feedReplyFixture()
        when:
        sut.claimFeedReply(feedReplyId);
        then:
        1 * feedActionRepository.save(_)
    }

    def "좋아요/신고하기를 취소할 수 있다."() {
        given:
        def feedActionId = 1
        def feedAction = feedLikeActionFixture() 
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
            feedLikeActionFixture()
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
            feedClaimActionFixture()
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
            feedClaimActionFixture()
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
            feedLikeActionFixture()
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
            feedClaimActionFixture()
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
            feedClaimActionFixture()
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
    
    def feedReplyFixture() {
        def feedReply = new FeedReply()
        feedReply.feed = feedFixture()
        feedReply.feedReplyId = 1
        return feedReply;
    }
    
    def feedLikeActionFixture() {
        def feedAction = new FeedAction()
        feedAction.feedActionId = 1
        feedAction.feedActionType = LIKE
        return feedAction;
    }
    
    def feedClaimActionFixture() {
        def feedAction = new FeedAction()
        feedAction.feedActionId = 1
        feedAction.feedActionType = CLAIM
        return feedAction;
    }
}
