package com.libqa.web.repository;

import com.libqa.application.util.PageUtil;
import com.libqa.testsupport.LibqaRepositoryTest;
import com.libqa.web.domain.QaContent;
import org.junit.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;

public class QaContentRepositoryTest extends LibqaRepositoryTest<QaContentRepository> {

    @Test
    public void findByIsDeletedFalse() {
        final Integer pageSize = 10;
        final Sort.Order order1 = new Sort.Order(Sort.Direction.DESC, "recommendCount");
        final Sort.Order order2 = new Sort.Order(Sort.Direction.ASC, "nonrecommendCount");
        PageRequest pageRequest = PageUtil.sortPageable(pageSize, new Sort(order1, order2));

        List<QaContent> qaContents = repository.findByIsDeletedFalse(pageRequest);
        System.out.println(qaContents);
    }

    @Test
    public void findAllBySearchValue() {
        final String searchValue = "test";
        List<QaContent> qaContents = repository.findAllBySearchValue(searchValue);
        System.out.println(qaContents);
    }
}