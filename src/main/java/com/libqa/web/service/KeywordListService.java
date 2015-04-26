package com.libqa.web.service;

import com.libqa.application.enums.KeywordTypeEnum;
import com.libqa.web.domain.KeywordList;

import java.util.List;

/**
 * Created by yong on 2015-03-08.
 *
 * @author yong
 */
public interface KeywordListService {
    boolean saveKeywordList(Object obj, KeywordTypeEnum keywordType);

    List<KeywordList> findByKeywordType(String keywordType, boolean isDeleted);
}
