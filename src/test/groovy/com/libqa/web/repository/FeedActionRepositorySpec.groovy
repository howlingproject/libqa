package com.libqa.web.repository
import com.libaqa.testsupport.LibQaRepositorySpecSupport
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired

class FeedActionRepositorySpec extends LibQaRepositorySpecSupport {
    @Autowired
    FeedActionRepository repository

    @Test
    def "feedId와 userId로 목록 조회"() {
        given:
        def feedId = -1L
        def userId = 1234
        when:
        def response = repository.findByFeedIdAndUserId(feedId, userId)
        then:
        response.size() == 0
    }

    @Test
    def "feedReplyId와 userId로 목록 조회"() {
        given:
        def feedReplyId = -1L
        def userId = 1234
        when:
        def response = repository.findByFeedReplyIdAndUserId(feedReplyId, userId)
        then:
        response.size() == 0
    }
}
