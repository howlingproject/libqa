package com.libqa.web.service;

import com.libqa.application.enums.KeywordTypeEnum;

/**
 * Created by yong on 2015-03-08.
 *
 * @author yong
 */
public interface KeywordListService {
    boolean saveKeywordList(Object obj, KeywordTypeEnum keywordType);
}
