package com.libqa.web.repository
import com.libaqa.testsupport.LibQaSpecification
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort

import static org.springframework.data.domain.Sort.Direction.DESC
import static org.springframework.data.domain.Sort.Order

class FeedRepositorySpec extends LibQaSpecification {
    @Autowired
    FeedRepository feedRepository

    @Test
    def "testFindByIsDeleted"() {
        given:
        PageRequest pageRequest = new PageRequest(0, 5, new Sort(new Order(DESC, "feedId")));
        when:
        def actual = feedRepository.findByIsDeleted(false, pageRequest)
        then:
        actual.size() >= 0
    }

}
