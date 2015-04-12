package com.libqa.application.enums;

import lombok.Getter;

/**
 * 가입 시점의 소셜 연동 타입
 * @Author : yion
 * @Date : 2015. 4. 12.
 * @Description :
 */
public enum SocialChannelTypeEnum {
    WEB("Web"),
    Mobile("Mobile"),
    FACEBOOK("Facebook"),
    TWITTER("Twitter")
    ;


    @Getter
    private String name;

    private SocialChannelTypeEnum(final String name) {
        this.name = name;
    }
}
