package com.libqa.application.enums;

/**
 * Created by yong on 15. 2. 1..
 */
public enum KeywordTypeEnum {
    WIKI("위키"),
    SPACE("공간"),
    QA("QA");

    private String type;

    private KeywordTypeEnum(String type) {
        this.type = type;
    }

    public String toString() {
        return this.type;
    }
}
