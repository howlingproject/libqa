package com.libqa.application.enums;

/**
 * Created by sjune on 2015-02-01.
 */
public enum SharedContentsTypeEnum {
    FB("Facebook"),
    TW("Twitter"),
    GP("GooglePlus");

    private String type;

    private SharedContentsTypeEnum(String type) {
        this.type = type;
    }

    public String toString() {
        return this.type;
    }

}
