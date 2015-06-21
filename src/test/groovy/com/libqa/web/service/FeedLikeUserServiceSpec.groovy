package com.libqa.web.service

import com.libqa.web.domain.Feed
import com.libqa.web.domain.FeedLikeUser
import com.libqa.web.repository.FeedLikeUserRepository
import spock.lang.Specification

import static com.libqa.application.enums.FeedLikeTypeEnum.FEED

class FeedLikeUserServiceSpec extends Specification {
    FeedLikeUserRepository repository
    FeedLikeUserService sut
    Integer USER_ID = -1
    Feed feed

    def setup() {
        repository = Mock(FeedLikeUserRepository)
        sut = new FeedLikeUserService(repository: repository)
        feed = new Feed('feedId': -1, userId: USER_ID)
    }
    
    def "좋아요/좋아요 취소를 한 적이 있으면 다시 좋아요를 할 수 있다."() {
        when:
        def response = sut.isLikable(null)
        then:
        response
    }

    def "좋아요 취소한 적이 있으면 다시 좋아요를 할 수 있다."() {
        when:        
        def response = sut.isLikable(FeedLikeUser.newInstance(isCanceled: true))
        then:
        response
    }

    def "좋아요를 한 적이 있으면 좋아요를 할 수 없다."() {
        when:
        def response = sut.isLikable(FeedLikeUser.newInstance(isCanceled: false))
        then:
        !response
    }

    def "가장 최근의 좋아요/좋아요취소를 가져온다."() {
        given:
        repository.findByFeedAndUserIdAndFeedLikeType(feed, USER_ID, FEED) >> [
                new FeedLikeUser(feedLikeUserId: 1, isCanceled: false, userId: USER_ID),
                new FeedLikeUser(feedLikeUserId: 2, isCanceled: true, userId: USER_ID),
                new FeedLikeUser(feedLikeUserId: 3, isCanceled: false, userId: USER_ID),
        ]
        when:
        def response = sut.getRecentlyFeedLikeUserBy(feed)
        then:
        response.feedLikeUserId == 3
    }
}
