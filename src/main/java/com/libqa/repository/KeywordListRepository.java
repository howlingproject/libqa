package com.libqa.repository;

import com.libqa.application.enums.KeywordTypeEnum;
import com.libqa.domain.Keyword;
import com.libqa.domain.KeywordList;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by yong on 15. 2. 8..
 */
public interface KeywordListRepository extends JpaRepository<KeywordList, Long> {
	KeywordList findByKeywordNameAndKeywordType(String keywordName, KeywordTypeEnum keywordTypeEnum);
}
