package com.libqa.web.repository

import com.libaqa.testsupport.LibQaSpecification
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired

class FeedRepositorySpec extends LibQaSpecification {
    @Autowired
    FeedRepository feedRepository

    @Test
    def "testFineOne"() {
        when:
        feedRepository.findOne(1L)
        then:
        1 == 1
    }

}
