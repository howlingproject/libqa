package com.libqa.application.enums;

import lombok.Getter;

/**
 * Created by yong on 15. 2. 1..
 */
public enum KeywordType {
    FEED("FEED"),  // 피드
    WIKI("WIKI"),  // 위키
    SPACE("SPACE"), // 공간
    QA("QA");  // QA

    @Getter
    private String code;

    private KeywordType(final String code) {
        this.code = code;
    }

}
