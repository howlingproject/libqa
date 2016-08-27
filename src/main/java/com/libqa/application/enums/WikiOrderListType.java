package com.libqa.application.enums;

/**
 * Created by songanji on 2016. 3. 12..
 */
public enum WikiOrderListType {
    INSERT_DATE("insertDate"),
    TITLE("title"),

    ;

    private String type;

    private WikiOrderListType(String type){ this.type = type; }

    public String toString(){ return this.type; }
}
