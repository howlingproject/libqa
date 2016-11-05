package com.libqa.web.service.common;

import com.libqa.application.enums.KeywordType;
import com.libqa.web.domain.KeywordList;

import java.util.List;

/**
 * Created by yong on 2015-03-08.
 *
 * @author yong
 */
public interface KeywordListService {
    List<KeywordList> findByKeywordType(String keywordType, boolean isDeleted);

    List<KeywordList> findByKeywordNameAndKeywordType(String keywordName, KeywordType keywordType);

    void save(KeywordList keyword);

    void save(List<KeywordList> keywordList);

}
