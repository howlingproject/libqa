package com.libqa.web.service;

import com.libqa.application.enums.KeywordTypeEnum;
import com.libqa.application.util.PageUtil;
import com.libqa.web.domain.Wiki;
import com.libqa.web.domain.WikiFile;
import com.libqa.web.repository.KeywordRepository;
import com.libqa.web.repository.WikiRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    final boolean isDeleted = false;

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
            keywordService.saveKeywordAndList(keywordArrays, KeywordTypeEnum.WIKI, result.getWikiId());
        }
    }

    @Override
    public Wiki findById(Integer wikiId) {
        return wikiRepository.findOne(wikiId);
    }

    @Override
    public List<Wiki> findByAllWiki(int page, int size) {
        List<Wiki> list = wikiRepository.findAllByIsDeleted(
                PageUtil.sortPageable(page, size, PageUtil.sortId("DESC", "insertDate")).getSort()
                ,isDeleted);
        if( list != null && list.size() > 0 ){
            for( Wiki wiki : list ){
                long replyCount = wikiReplyService.countByWikiWikiId(wiki.getWikiId());
                wiki.setReplyCount(replyCount);
                wiki.setKeywordList(keywordService.findByWikiId(wiki.getWikiId(), isDeleted));
            }
        }
        return list;
    }

    @Override
    public List<Wiki> findByBestWiki(int page, int size) {
        List<Wiki> list = wikiRepository.findAllByIsDeleted(
                PageUtil.sortPageable(page, size, PageUtil.sortId("DESC", "likeCount")).getSort()
                ,isDeleted);
        if( list != null && list.size() > 0 ){
            for( Wiki wiki : list ){
                long replyCount = wikiReplyService.countByWikiWikiId(wiki.getWikiId());
                wiki.setReplyCount(replyCount);
                wiki.setKeywordList(keywordService.findByWikiId(wiki.getWikiId(), isDeleted));
            }
        }
        return list;
    }

    @Override
    public List<Wiki> findByRecentWiki(int userId, int page, int size) {
        List<Wiki> list = wikiRepository.findAllByUserIdAndIsDeleted(
                userId
                , PageUtil.sortPageable(
                        page
                        , size
                        , PageUtil.sort(PageUtil.order("DESC", "userId"), PageUtil.order("DESC", "insertDate"))
                ).getSort()
                , isDeleted);
        if( list != null && list.size() > 0 ){
            for( Wiki wiki : list ){
                wiki.setKeywordList(keywordService.findByWikiId(wiki.getWikiId(), isDeleted));
            }
        }
        return list;
    }

    @Override
    public List<Wiki> findByAllCondition(boolean isDeleted) {
        return wikiRepository.findAllByIsDeleted(PageUtil.sortId("ASC", "wikiId"), isDeleted);
    }

    @Override
    public List<Wiki> findBySpaceId(Integer spaceId) {
        return wikiRepository.findBySpaceIdAndIsDeleted(spaceId, false);
    }

}
