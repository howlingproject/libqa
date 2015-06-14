package com.libqa.web.service

import com.libqa.web.domain.Feed
import com.libqa.web.domain.FeedLikeUser
import com.libqa.web.repository.FeedLikeUserRepository
import spock.lang.Specification

import static com.libqa.application.enums.FeedLikeTypeEnum.FEED

class FeedLikeUserServiceSpec extends Specification {
    FeedLikeUserRepository repository
    FeedLikeUserService service
    Feed feed = new Feed('feedId': -1, userId: -1)

    def setup() {
        repository = Mock(FeedLikeUserRepository)
        service = new FeedLikeUserService(repository: repository)
    }

    def "like를 한 적이 없다면 like"() {
        given:
        repository.findByFeedAndUserIdAndFeedLikeType(feed, feed.getUserId(), FEED) >> []
        when:
        service.likeOrUnlike(feed)
        then:
        1 * repository.save(_) >> { FeedLikeUser feedLikeUser ->
            assert feedLikeUser.isCanceled() == false
        }
    }

    def "가장 최근에 disLike를 했다면 like"() {
        given:
        repository.findByFeedAndUserIdAndFeedLikeType(feed, feed.getUserId(), FEED) >> [
                new FeedLikeUser(feedLikeUserId: 1, isCanceled: false),
                new FeedLikeUser(feedLikeUserId: 2, isCanceled: true)
        ]
        when:
        service.likeOrUnlike(feed)
        then:
        1 * repository.save(_) >> { FeedLikeUser feedLikeUser ->
            assert feedLikeUser.isCanceled() == false
        }
    }

    def "가장 최근에 like를 했다면 disLike"() {
        given:
        repository.findByFeedAndUserIdAndFeedLikeType(feed, feed.getUserId(), FEED) >> [
                new FeedLikeUser(feedLikeUserId: 1, isCanceled: false),
                new FeedLikeUser(feedLikeUserId: 2, isCanceled: true),
                new FeedLikeUser(feedLikeUserId: 3, isCanceled: false),
        ]
        when:
        service.likeOrUnlike(feed)
        then:
        1 * repository.save(_) >> { FeedLikeUser feedLikeUser ->
            assert feedLikeUser.isCanceled() == true
        }
    }

}
