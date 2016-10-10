package com.libqa.application.dto;

import lombok.Data;

/**
 * Created by yong on 2015-05-15.
 *
 * @author yong
 */
@Data
public class QaDto {
    private String keywordName;

    private String keywordType;

    private String dayType;

    private String waitReply;

    private Integer lastQaId;
}
