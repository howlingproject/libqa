package com.libqa.application.enums;

import lombok.Getter;

/**
 * Created by yion on 2015. 2. 20..
 */
public enum StatusCode {
    FAIL(-1, "FAIL"),
    NONE(0, "NA"),
    SUCCESS(1, "SUCCESS"),
    NEED_LOGIN(10, "로그인이 필요합니다"),
    NOT_MATCH_USER(11, "사용자가 일치하지 않습니다."),
    EXIST_REPLY(21, "해당 게시물에 답변이 존재합니다."),
    EXIST_RECOMMEND(22, "해당 게시물에 추천 또는 비추천이 존재합니다."),
    EXIST_CHOICE(23, "해당 게시물에 선정된 답변이 존재합니다"),
    DUPLICATE(99, "중복된 데이터"),
    SIZE_LIMIT_EXCEEDED(100, "허용 용량 초과"),
    INVALID_FILE(101, "올바른 파일 형식이 아닙니다."),
    INVALID_STATUS(102, "잘못된 상태"),
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
