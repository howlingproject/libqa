package com.libqa.application.enums;

import lombok.Getter;

/**
 * Created by yong on 2016. 8. 18..
 */
public enum QaSearchType {
    TOTAL("TOTAL"),
    WAIT_REPLY("WAIT_REPLY");

    @Getter
    private String name;

    private QaSearchType(final String name){
        this.name = name;
    }
}
