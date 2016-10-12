package com.libqa.application.dto;

import com.libqa.application.enums.DayType;
import com.libqa.application.enums.QaSearchType;
import com.libqa.application.enums.WaitReplyType;
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

    private DayType dayType;

    private WaitReplyType waitReplyType;

    private QaSearchType qaSearchType;

    private Integer lastQaId;
}
