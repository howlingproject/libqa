package com.libqa.web.repository;

import com.libqa.application.enums.KeywordTypeEnum;
import com.libqa.web.domain.KeywordList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by yong on 15. 2. 8..
 */
public interface KeywordListRepository extends JpaRepository<KeywordList, Long> {
	List<KeywordList> findByKeywordNameAndKeywordType(String keywordName, KeywordTypeEnum keywordType);
}
