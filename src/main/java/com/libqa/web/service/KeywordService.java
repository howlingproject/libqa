package com.libqa.web.service;

import com.libqa.application.enums.KeywordTypeEnum;

/**
 * Created by yong on 2015-03-08.
 *
 * @author yong
 */
public interface KeywordService {

    boolean saveKeywordAndList(String[] keywordNames, KeywordTypeEnum keywordType, Integer entityId);
}
