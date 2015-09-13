package com.libqa.web.service;

import com.libqa.application.enums.KeywordType;
import com.libqa.web.domain.KeywordList;

import java.util.List;

/**
 * Created by yong on 2015-03-08.
 *
 * @author yong
 */
public interface KeywordListService {
    boolean saveKeywordList(Object obj, KeywordType keywordType);

    List<KeywordList> findByKeywordType(String keywordType, boolean isDeleted);
}
