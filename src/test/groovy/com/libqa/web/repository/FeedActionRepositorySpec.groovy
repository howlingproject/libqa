package com.libqa.web.repository
import com.libaqa.testsupport.LibQaRepositorySpecSuppoort
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired

import static com.libqa.application.enums.FeedActionTypeEnum.LIKE

class FeedActionRepositorySpec extends LibQaRepositorySpecSuppoort {
    @Autowired
    FeedActionRepository repository

    @Test
    def "특정 feed의 like한 목록 조회"() {
        given:
        def feedId = -1L
        def userId = 1234
        when:
        def response = repository.findByFeedIdAndUserIdAndFeedActionType(feedId, userId, LIKE)
        then:
        response.size() == 0
    }
}
