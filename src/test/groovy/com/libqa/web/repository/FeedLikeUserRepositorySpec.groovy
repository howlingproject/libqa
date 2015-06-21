package com.libqa.web.repository
import com.libaqa.testsupport.LibQaRepositorySpecSuppoort
import com.libqa.web.domain.Feed
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired

import static com.libqa.application.enums.FeedLikeTypeEnum.FEED

class FeedLikeUserRepositorySpec extends LibQaRepositorySpecSuppoort {
    @Autowired
    FeedLikeUserRepository repository

    @Test
    def "특정 feed의 like한 목록 조회"() {
        given:
        def feed = new Feed('feedId': -1, 'userId': -1)
        when:
        def response = repository.findByFeedAndUserIdAndFeedLikeType(feed, feed.getUserId(), FEED)
        then:
        response.size() == 0
    }
}
