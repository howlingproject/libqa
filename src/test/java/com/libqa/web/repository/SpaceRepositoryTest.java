package com.libqa.web.repository;

import com.libqa.testsupport.LibqaRepositoryTest;
import com.libqa.web.domain.Space;
import org.junit.Test;

import java.util.List;

public class SpaceRepositoryTest extends LibqaRepositoryTest<SpaceRepository> {
    @Test
    public void findAllBySearchValue() {
        final String searchValue = "test";
        List<Space> qaContents = repository.findAllBySearchValue(searchValue);
        System.out.println(qaContents);
    }
}