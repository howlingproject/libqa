package com.libqa.application.enums;

import lombok.Getter;

/**
 * Created by yion on 2015. 2. 20..
 */
public enum StatusCodeEnum {
    FAIL(-1, "FAIL"),
    NONE(0, "NA"),
    SUCCESS(1, "SUCCESS"),
    DUPLICATE(99, "중복된 데이터"),
    SIZE_LIMIT_EXCEEDED(100, "허용 용량 초과"),
    INVALID_STATUS(102, "잘못된 상태"),
    INVALID_PARAMETER(900, "잘못된 파라미터"),
    INTERNAL(990, "내부 오류"),
    SYSTEM_ERROR(999, "시스템 오류");

    @Getter
    private final int code;

    @Getter
    private final String comment;

    private StatusCodeEnum(final int code, final String comment) {
        this.code = code;
        this.comment = comment;
    }

}
