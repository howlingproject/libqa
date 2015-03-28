package com.libqa.web.service;

import com.libqa.application.enums.KeywordTypeEnum;
import com.libqa.web.domain.Keyword;

import java.util.List;

/**
 * Created by yong on 2015-03-08.
 *
 * @author yong
 */
public interface KeywordService {

    boolean saveKeywordAndList(String[] keywordNames, KeywordTypeEnum keywordType, Integer entityId);

    List<Keyword> findByQaId(Integer qaId, boolean isDeleted);
}
