package com.libqa.application.enums;

import lombok.Getter;

/**
 * @Author : yion
 * @Date : 2015. 8. 21.
 * @Description :
 */

public enum ActivityKindEnum {
    CREATE_SPACE("공간을 생성 하였습니다."),
    UPDATE_SPACE("공간을 수정 하였습니다."),
    INSERT_WIKI("위키를 생성 하였습니다."),
    UPDATE_WIKI("위키를 수정 하였습니다."),
    INSERT_REPLY_WIKI("위키에 댓글 생성 하였습니다."),
    ADD_SPACE_FAVORITE("공간을 즐겨찾기에 추가 하였습니다."),
    ADD_WIKI_FAVORITE("위키를 즐겨찾기에 추가 하였습니다."),
    ADD_VOTE_YES("추천에 투표 하였습니다."),
    ADD_VOTE_NO("비추천에 투표 하였습니다."),
    ADD_REPLY_RECOMMEND("댓글을 추천 하였습니다.")
    ;

    @Getter
    private String code;

    private ActivityKindEnum(final String code) {
        this.code = code;
    }

}
