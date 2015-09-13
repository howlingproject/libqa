package com.libqa.application.enums;

import lombok.Getter;

/**
 * Created by yong on 2015-04-25.
 *
 * @author yong
 */
public enum DayType {
    TODAY("TODAY"),  // 오늘
    WEEK("WEEK"), // 일주일
    ALL("ALL");  // 전체

    @Getter
    private String code;

    private DayType(final String code) {
        this.code = code;
    }
}
