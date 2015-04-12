package com.libqa.web.service;

import com.libqa.application.enums.KeywordTypeEnum;
import com.libqa.web.domain.Wiki;
import com.libqa.web.repository.WikiRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by songanji on 2015. 3. 1..
 */
@Service
@Slf4j
public class WikiServiceImpl implements WikiService {
    @Autowired
    WikiRepository wikiRepository;

    @Autowired
    private KeywordService keywordService;

    @Override
    public Wiki save(Wiki wiki) {
        return wikiRepository.save(wiki);
    }

    @Override
    public Wiki saveWithKeyword(Wiki wiki) {
        Wiki result = save(wiki);

        String[] keywordArrays = wiki.getKeywords().split(",");
        log.info(" keywordArrays : {}", keywordArrays.length);
        if (keywordArrays.length > 0) {
            keywordService.saveKeywordAndList(keywordArrays, KeywordTypeEnum.WIKI, result.getSpaceId());
        }
        return result;
    }

    @Override
    public Wiki findById(Integer wikiId) {
        return wikiRepository.findOne(wikiId);
    }

    @Override
    public List<Wiki> findByAllWiki(int startIdx, int endIdx) {
        PageRequest pageRequest = new PageRequest(startIdx, endIdx, new Sort(new Sort.Order(Sort.Direction.DESC, "insertDate")));
        return wikiRepository.findAll(pageRequest).getContent();
    }

    @Override
    public List<Wiki> findByBestWiki(int startIdx, int endIdx) {
        return null;
    }

    @Override
    public List<Wiki> findByRecentWiki(int userId, int startIdx, int endIdx) {
        PageRequest pageRequest = new PageRequest(startIdx, endIdx,
                new Sort(new Sort.Order(Sort.Direction.DESC, "userId")
                ,new Sort.Order(Sort.Direction.DESC, "insertDate")
                )

        );

        return wikiRepository.findAll(pageRequest).getContent();
    }


}
