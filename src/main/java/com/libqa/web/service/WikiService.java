package com.libqa.web.service;

import com.libqa.web.domain.Wiki;
import com.libqa.web.domain.WikiFile;

import java.util.List;

/**
 * Created by songanji on 2015. 3. 1..
 */
public interface WikiService {
    Wiki save(Wiki wiki);

    Wiki saveWithKeyword(Wiki wiki);

    Wiki findById(Integer wikiId);

    List<Wiki> findByAllWiki(int startIdx, int endIdx);

    List<Wiki> findByBestWiki(int startIdx, int endIdx);

    List<Wiki> findByRecentWiki(int userId, int startIdx, int endIdx);

    List<Wiki> findByAllCondition(boolean isDeleted);
}
