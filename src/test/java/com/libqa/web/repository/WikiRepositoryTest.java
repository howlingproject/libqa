package com.libqa.web.repository;

import com.libqa.application.util.PageUtil;
import com.libqa.testsupport.LibqaRepositoryTest;
import com.libqa.web.domain.Wiki;
import org.junit.Test;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public class WikiRepositoryTest extends LibqaRepositoryTest<WikiRepository> {

    @Test
    public void findBySpaceIdAndIsDeletedFalse() {
        final int spaceId = 90;
        final PageRequest pageRequest = PageUtil.sortPageable(1, PageUtil.sortId("DESC", "wikiId"));
        List<Wiki> wikies = repository.findAllBySpaceIdAndIsDeletedFalse(pageRequest, spaceId);
        System.out.println(wikies);
    }
}