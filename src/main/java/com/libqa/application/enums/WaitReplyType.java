package com.libqa.application.enums;

import lombok.Getter;

/**
 * Created by yong on 2016-10-12.
 *
 * @author yong
 */
public enum WaitReplyType {
    WAIT("WAIT"),   // 답변을 기다리는
    REPLIED("REPLIED"); // 답변 존재

    @Getter
    private String code;

    private WaitReplyType(final String code){ this.code = code; }
}
