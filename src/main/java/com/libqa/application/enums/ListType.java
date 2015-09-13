package com.libqa.application.enums;

import lombok.Getter;

/**
 * Created by songanji on 2015. 6. 28..
 */
public enum ListType {
    BEST("best"),
    ALL("all"),
    RESENT("resent"),
    KEYWORD("keyword")
    ;

    @Getter
    private String name;

    private ListType(final String name) {
        this.name = name;
    }
    ;
}
