package com.libqa.web.repository;

import com.libqa.application.enums.KeywordType;
import com.libqa.web.domain.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by yong on 15. 2. 8..
 */
public interface KeywordRepository extends JpaRepository<Keyword, Integer> {

    List<Keyword> findAllByQaIdAndIsDeletedFalse(Integer qaId);

    List<Keyword> findAllByKeywordTypeAndIsDeleted(KeywordType keywordType, boolean isDeleted);

    List<Keyword> findAllByKeywordTypeAndKeywordNameAndIsDeleted(KeywordType keywordType, String keywordName, boolean isDeleted);

    List<Keyword> findAllByWikiIdAndIsDeleted(Integer wikiId, boolean isDeleted);

    List<Keyword> findAllByKeywordIdAndIsDeleted(Integer keywordId, boolean isDeleted);

    List<Keyword> findAllBySpaceIdAndIsDeleted(Integer spaceId, boolean isDeleted);


}
