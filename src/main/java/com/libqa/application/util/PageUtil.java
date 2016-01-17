package com.libqa.application.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

/**
 * Created by yion on 2015. 3. 20..
 */
public class PageUtil {
    private static final int DEFAULT_PAGE = 0;
    private static final int DEFAULT_SIZE = 5;

    public static Sort sortId(String direction, String columnId) {
        if (direction.equals("DESC"))
            return new Sort(Sort.Direction.DESC, columnId);
        else
            return new Sort(Sort.Direction.ASC, columnId);
    }

    public static Sort sort(Sort.Order order1, Sort.Order order2) {
        return new Sort(order1, order2);
    }

    public static Sort.Order order(String direction, String columnId) {
        if (direction.equals("DESC"))
            return new Sort.Order(Sort.Direction.DESC, columnId);
        else
            return new Sort.Order(Sort.Direction.ASC, columnId);
    }

    public static PageRequest sortPageable(int page, int size, Sort sort) {
        return new PageRequest(page, size, sort);
    }

    public static PageRequest sortPageable(int size, Sort sort) {
        return new PageRequest(DEFAULT_PAGE, size, sort);
    }

    public static PageRequest sortPageable(Sort sort) {
        return new PageRequest(DEFAULT_PAGE, DEFAULT_SIZE, sort);
    }


}
