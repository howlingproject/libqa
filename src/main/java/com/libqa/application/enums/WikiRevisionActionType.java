package com.libqa.application.enums;

/**
 * Created by songanji on 2015. 2. 8..
 */
public enum WikiRevisionActionType {
    UPDATE_WIKI("위키 글쓰기"),
    UPDATE_TITLE("QA 답변"),
    UPDATE_FILE("첨부파일"),
    UPDATE_DELETE("내용삭제");

    private String type;

    private WikiRevisionActionType(String type) {
        this.type = type;
    }

    public String toString() {
        return this.type;
    }
}
