package com.libqa.web.service;

import com.libqa.application.enums.WikiRevisionActionTypeEnum;
import com.libqa.web.domain.Keyword;
import com.libqa.web.domain.Wiki;

import java.util.List;

/**
 * Created by songanji on 2015. 3. 1..
 */
public interface WikiService {
    Wiki save(Wiki wiki);

    Wiki saveWithKeyword(Wiki wiki, Keyword keyword);

    Wiki updateWithKeyword(Wiki wiki, Keyword keyword, WikiRevisionActionTypeEnum revisionActionTypeEnum);

    Wiki findById(Integer wikiId);

    Wiki findByParentId(Integer parentId);

    List<Wiki> findBySubWikiId(Integer wikiId);

    List<Wiki> findByAllWiki(int startIdx, int endIdx);

    List<Wiki> findByBestWiki(int startIdx, int endIdx);

    List<Wiki> findByRecentWiki(int userId, int startIdx, int endIdx);

    List<Wiki> findAllByCondition();

    List<Wiki> findBySpaceId(Integer spaceId);

    List<Wiki> findSortAndModifiedBySpaceId(Integer spaceId, int startIdx, int endIdx);

    List<Wiki> findWikiListByKeyword(String keywordNm, int page, int size);

    List<Wiki> findWikiListByContentsMarkup(String searchText, int page, int size);
}
