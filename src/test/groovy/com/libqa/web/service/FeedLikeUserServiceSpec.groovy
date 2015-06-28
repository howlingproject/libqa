package com.libqa.web.service

import com.libqa.web.domain.Feed
import com.libqa.web.domain.FeedAction
import com.libqa.web.repository.FeedActionRepository
import spock.lang.Specification

import static com.libqa.application.enums.FeedActionTypeEnum.LIKE

class FeedLikeUserServiceSpec extends Specification {
    FeedActionRepository repository
    FeedActionService sut
    Feed feed
    
    def setup() {
        repository = Mock(FeedActionRepository)
        sut = new FeedActionService(repository: repository)
        feed = new Feed('feedId': -1, userId: 1234)
    }
    
    def "좋아요/좋아요 취소를 한 적이 있으면 다시 좋아요를 할 수 있다."() {
        when:
        def response = sut.isLikable(null)
        then:
        response
    }

    def "좋아요 취소한 적이 있으면 다시 좋아요를 할 수 있다."() {
        when:        
        def response = sut.isLikable(FeedAction.newInstance(isCanceled: true))
        then:
        response
    }

    def "좋아요를 한 적이 있으면 좋아요를 할 수 없다."() {
        when:
        def response = sut.isLikable(FeedAction.newInstance(isCanceled: false))
        then:
        !response
    }

    def "가장 최근의 좋아요/좋아요취소를 가져온다."() {
        given:
        repository.findByFeedIdAndUserIdAndFeedActionType(feed.getFeedId(), feed.getUserId(), LIKE) >> [
                new FeedAction(feedActionId: 1, isCanceled: false, userId: feed.getUserId()),
                new FeedAction(feedActionId: 2, isCanceled: true, userId: feed.getUserId()),
                new FeedAction(feedActionId: 3, isCanceled: false, userId: feed.getUserId())
        ]
        when:
        def response = sut.getRecentlyFeedLikeUserBy(feed)
        then:
        response.feedActionId == 3
    }
}
