package com.libaqa.testsupport

import com.libqa.Application
import org.junit.runner.RunWith
import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.test.context.transaction.TransactionConfiguration
import org.springframework.test.context.web.WebAppConfiguration
import spock.lang.Specification

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("integration-test")
@WebAppConfiguration
@ContextConfiguration(loader = SpringApplicationContextLoader.class, classes = Application.class)
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
abstract class LibQaRepositorySpecSupport extends Specification {
}
