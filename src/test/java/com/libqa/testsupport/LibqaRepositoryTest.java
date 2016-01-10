package com.libqa.testsupport;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@SpringApplicationConfiguration(classes = RepositoryTestConfiguration.class)
@Transactional
@ActiveProfiles("spring-test")
@RunWith(SpringJUnit4ClassRunner.class)
public abstract class LibqaRepositoryTest<T> {
    @Autowired
    protected T repository;
}
