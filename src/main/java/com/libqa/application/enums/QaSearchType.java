package com.libqa.application.enums;

import lombok.Getter;

/**
 * Created by yong on 2016. 8. 18..
 */
public enum QaSearchType {
    TOTAL("total"),
    WAIT_REPLY("waitReply");

    @Getter
    private String name;

    private QaSearchType(final String name){
        this.name = name;
    }
}
