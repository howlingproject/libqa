package com.libqa.web.repository
import com.libaqa.testsupport.LibQaRepositorySpecSuppoort
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired

class FeedReplyRepositoryTest extends LibQaRepositorySpecSuppoort {
    @Autowired
    FeedReplyRepository repository

    @Test
    def "id로 reply 조회"() {
        when:
        def response = repository.findOne(-1L)
        then:
        response == null
    }
}
