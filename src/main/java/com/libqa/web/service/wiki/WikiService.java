package com.libqa.web.service.wiki;

import com.libqa.application.enums.WikiRevisionActionType;
import com.libqa.web.domain.Keyword;
import com.libqa.web.domain.Wiki;
import com.libqa.web.domain.WikiLike;
import com.libqa.web.view.DisplayWiki;
import com.libqa.web.view.DisplayWikiLike;

import java.util.List;

/**
 * Created by songanji on 2015. 3. 1..
 */
public interface WikiService {
    Wiki save(Wiki wiki);

    Wiki saveWithKeyword(Wiki wiki, Keyword keyword);

    Wiki updateWithKeyword(Wiki wiki, Keyword keyword, WikiRevisionActionType revisionActionTypeEnum);

    Wiki findById(Integer wikiId);

    Wiki findByParentId(Integer parentId);

    List<Wiki> findBySubWikiId(Integer wikiId);

    List<DisplayWiki> findByAllWiki(int startIdx, int endIdx);

    List<DisplayWiki> findByBestWiki(int startIdx, int endIdx);

    List<DisplayWiki> findByRecentWiki(int userId, int startIdx, int endIdx);

    List<Wiki> findAllByCondition();

    List<Wiki> findBySpaceId(Integer spaceId);

    List<Wiki> findSortAndModifiedBySpaceId(Integer spaceId, int startIdx, int endIdx);

    List<Wiki> findWikiListByKeyword(String keywordNm, int page, int size);

    List<Wiki> findWikiListByContentsMarkup(String searchText, int page, int size);

    DisplayWikiLike updateLike(WikiLike likes);

    Long countByAllWiki();

    List<DisplayWiki> findUpdateWikiList(int startIdx, int endIdx);

}
