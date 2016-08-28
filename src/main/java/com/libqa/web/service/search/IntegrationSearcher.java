package com.libqa.web.service.search;

import com.libqa.web.domain.QaContent;
import com.libqa.web.domain.Space;
import com.libqa.web.domain.Wiki;
import com.libqa.web.repository.QaContentRepository;
import com.libqa.web.repository.SpaceRepository;
import com.libqa.web.repository.WikiRepository;
import com.libqa.web.view.search.DisplaySearchResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class IntegrationSearcher {
    @Autowired
    private DisplaySearchResultBuilder displaySearchResultBuilder;
    @Autowired
    private QaContentRepository qaRepository;
    @Autowired
    private SpaceRepository spaceRepository;
    @Autowired
    private WikiRepository wikiRepository;

    /**
     * searchTargetPage에 맞는 query 결과를 return 한다.
     *
     * @param searchTargetPage
     * @param query
     * @return list of DisplaySearchResult
     */
    public List<DisplaySearchResult> search(SearchTargetPage searchTargetPage, String query) {
        log.debug("searchTargetPage:{}, query:{}", searchTargetPage, query);

        if (searchTargetPage.isQA()) {
            List<QaContent> qaContents = qaRepository.findAllBySearchValue(query);
            return displaySearchResultBuilder.buildByQaContent(qaContents);
        }

        if (searchTargetPage.isSpace()) {
            List<Space> spaces = spaceRepository.findAllBySearchValue(query);
            return displaySearchResultBuilder.buildBySpace(spaces);
        }

        List<Wiki> wikies = wikiRepository.findAllBySearchValue(query);
        return displaySearchResultBuilder.buildByWiki(wikies);
    }
}
