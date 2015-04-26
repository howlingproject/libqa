package com.libqa.web.service;

import com.libqa.application.enums.KeywordTypeEnum;
import com.libqa.web.domain.Wiki;
import com.libqa.web.domain.WikiFile;
import com.libqa.web.repository.KeywordRepository;
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
    KeywordRepository keywordRepository;

    @Autowired
    private WikiFileService wikiFileService;

    @Autowired
    private KeywordService keywordService;

    @Autowired
    private WikiReplyService wikiReplyService;

    @Override
    public Wiki save(Wiki wiki) {
        return wikiRepository.save(wiki);
    }

    @Override
    public Wiki saveWithKeyword(Wiki wiki) {
        Wiki result = save(wiki);
        saveWikiFileAndList(result);
        saveKeywordAndList(wiki, result);
        return result;
    }

    private void saveWikiFileAndList(Wiki wiki) {
        List<WikiFile> wikiFiles= wiki.getWikiFiles();
        Wiki tempWiki = new Wiki();
        tempWiki.setWikiId(wiki.getWikiId());
        if( wikiFiles != null && wikiFiles.size() > 0){
            for( WikiFile wikiFile : wikiFiles ){
                wikiFile.setInsertDate( wiki.getInsertDate());
                wikiFile.setUserId(wiki.getUserId() );
                wikiFile.setWikiId( wiki.getWikiId() );
                wikiFileService.saveWikiFileAndList(wikiFile);
            }
        }
    }

    private void saveKeywordAndList(Wiki wiki, Wiki result) {
        String[] keywordArrays = wiki.getKeywords().split(",");
        log.info(" keywordArrays : {}", keywordArrays.length);
        if (keywordArrays.length > 0) {
            keywordService.saveKeywordAndList(keywordArrays, KeywordTypeEnum.WIKI, result.getSpaceId());
        }
    }

    @Override
    public Wiki findById(Integer wikiId) {
        return wikiRepository.findOne(wikiId);
    }

    @Override
    public List<Wiki> findByAllWiki(int startIdx, int endIdx) {
        PageRequest pageRequest = new PageRequest(startIdx, endIdx, new Sort(new Sort.Order(Sort.Direction.DESC, "insertDate")));
        List<Wiki> list = wikiRepository.findAll(pageRequest).getContent();
        if( list != null && list.size() > 0 ){
            for( Wiki wiki : list ){
                long replyCount = wikiReplyService.countByWikiWikiId(wiki.getWikiId());
                wiki.setReplyCount(replyCount);
            }
        }

        return list;
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
