package com.libqa.application.enums;

import lombok.Getter;

/**
 * Created by yion on 2015. 2. 20..
 */
public enum StatusCode {
    FAIL(-1, "FAIL"),
    NONE(0, "NA"),
    SUCCESS(1, "SUCCESS"),
    DUPLICATE(99, "중복된 데이터"),
    SIZE_LIMIT_EXCEEDED(100, "허용 용량 초과"),
    INVALID_STATUS(102, "잘못된 상태"),
    NEED_LOGIN(200, "로그인이 필요합니다"),
    NOT_MATCH_USER(300, "사용자가 일치하지 않습니다."),
    INVALID_PARAMETER(900, "잘못된 파라미터"),
    INTERNAL(990, "내부 오류"),
    SYSTEM_ERROR(999, "시스템 오류");

    @Getter
    private final int code;

    @Getter
    private final String comment;

    StatusCode(final int code, final String comment) {
        this.code = code;
        this.comment = comment;
    }

}
