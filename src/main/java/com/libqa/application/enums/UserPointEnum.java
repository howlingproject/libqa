package com.libqa.application.enums;

/**
 * Created by yion on 2015. 2. 1..
 */
public enum UserPointEnum {
    WRITE_WIKI("위키 글쓰기"),
    QA_REPLY("QA 답변"),
    QA_RECOMMAND("QA 추천"),
    SHARE_COUNT("공유 갯수"),
    LOGIN_COUNT("로그인 횟수"),
    WRITE_FEED("FEED 쓰기"),
    FEED_REPLY("FEED 답변"),
    FEED_LIKE("FEED 좋아요"),
    ADD_FAVORITE("즐겨찾기 추가");



    private String type;

    private UserPointEnum(String type) {
        this.type = type;
    }

    public String toString() {
        return this.type;
    }
}
