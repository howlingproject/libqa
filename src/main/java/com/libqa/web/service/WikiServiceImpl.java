package com.libqa.web.service;

import com.libqa.application.enums.KeywordTypeEnum;
import com.libqa.application.enums.WikiRevisionActionTypeEnum;
import com.libqa.application.util.PageUtil;
import com.libqa.web.domain.Keyword;
import com.libqa.web.domain.Wiki;
import com.libqa.web.domain.WikiFile;
import com.libqa.web.domain.WikiSnapShot;
import com.libqa.web.repository.KeywordRepository;
import com.libqa.web.repository.WikiRepository;
import com.libqa.web.repository.WikiSnapShotRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
    WikiSnapShotRepository wikiSnapShotRepository;

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
    @Transactional
    public Wiki saveWithKeyword(Wiki wiki) {
        Wiki result = save(wiki);
        saveWikiFileAndList(result);
        saveKeywordAndList(wiki, result);
        return result;
    }

    @Override
    @Transactional
    public Wiki updateWithKeyword(Wiki wiki, WikiRevisionActionTypeEnum revisionActionTypeEnum) {
        Wiki snapShotwiki = findById(wiki.getWikiId());
        WikiSnapShot wikiSnapShot = new WikiSnapShot();
        BeanUtils.copyProperties(wiki, wikiSnapShot);

        wikiSnapShot.setWiki( snapShotwiki );
        wikiSnapShot.setParentsId(snapShotwiki.getParentsId());
        wikiSnapShot.setRevisionActionType(revisionActionTypeEnum);
        wikiSnapShotRepository.save(wikiSnapShot);

        Wiki result = save(wiki);
        // 아래는 버그가 있는듯. 확인해서 다시 정상화하도록.
        //saveWikiFileAndList(result);
        //saveKeywordAndList(wiki, result);

        return result;
    }

    private void saveWikiFileAndList(Wiki wiki) {
        List<WikiFile> wikiFiles = wiki.getWikiFiles();
        Wiki tempWiki = new Wiki();
        tempWiki.setWikiId(wiki.getWikiId());
        if (wikiFiles != null && wikiFiles.size() > 0) {
            for (WikiFile wikiFile : wikiFiles) {
                wikiFile.setInsertDate(wiki.getInsertDate());
                wikiFile.setUserId(wiki.getUserId());
                wikiFile.setWikiId(wiki.getWikiId());
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
                , isDeleted);
        return list;
    }

    @Override
    public List<Wiki> findByBestWiki(int page, int size) {
        List<Wiki> list = wikiRepository.findAllByIsDeleted(
                PageUtil.sortPageable(page, size, PageUtil.sortId("DESC", "likeCount")).getSort()
                , isDeleted);
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
        return list;
    }

    @Override
    public List<Wiki> findAllByCondition() {
        return wikiRepository.findAllByIsDeleted(PageUtil.sortId("ASC", "wikiId"), isDeleted);
    }

    @Override
    public List<Wiki> findBySpaceId(Integer spaceId) {
        return wikiRepository.findBySpaceIdAndIsDeleted(spaceId, isDeleted);
    }

    @Override
    public List<Wiki> findSortAndModifiedBySpaceId(Integer spaceId, int startIdx, int endIdx) {
        List wikis = new ArrayList<>();
        try {
            wikis = wikiRepository.findAllBySpaceIdAndIsDeleted(spaceId, isDeleted,
                    PageUtil.sortPageable(startIdx, endIdx, PageUtil.sortId("DESC", "updateDate")).getSort());
        } catch (Exception e) {
            e.getMessage();
        }

        return wikis;
    }

    @Override
    public List<Wiki> findWikiListByKeyword(String keywordNm, int page, int size) {
        List<Keyword> keywordList = keywordRepository.findAllByKeywordTypeAndKeywordNameAndIsDeleted(KeywordTypeEnum.WIKI, keywordNm, false);
        List<Integer> wikiIds = getWikiIdByKeyworld(keywordList);

        List<Wiki> list = wikiRepository.findAllByWikiIdInAndIsDeleted(
                wikiIds
                , PageUtil.sortPageable(
                        page
                        , size
                        , PageUtil.sortId("DESC", "insertDate")
                ).getSort()
                , isDeleted);
        return list;
    }

    @Override
    public List<Wiki> findWikiListByContentsMarkup(String searchText, int page, int size) {
        List<Wiki> list = wikiRepository.findAllByContentsMarkupContainingAndIsDeleted(
                searchText
                , PageUtil.sortPageable(
                        page
                        , size
                        , PageUtil.sortId("DESC", "insertDate")
                ).getSort()
                , isDeleted);
        return list;
    }

    private List<Integer> getWikiIdByKeyworld(List<Keyword> keywords){
        List<Integer> wikiIds = new ArrayList<>();
        for( Keyword keyword : keywords ){
            wikiIds.add(keyword.getWikiId());
        }
        return wikiIds;
    }


    private WikiSnapShot saveSnapShot(Wiki wiki, WikiRevisionActionTypeEnum revisionActionTypeEnum) {

        WikiSnapShot wikiSnapShot = new WikiSnapShot();
        BeanUtils.copyProperties(wiki, wikiSnapShot);
        wikiSnapShot.setRevisionActionType(revisionActionTypeEnum);

//
//        wikiSnapShot.setSpaceId(wiki.getSpaceId());
//        wikiSnapShot.setParentsId(wiki.getParentsId());
//        wikiSnapShot.setTitle(wiki.getTitle());
//        wikiSnapShot.setOrderIdx(wiki.getOrderIdx());
//        wikiSnapShot.setDepthIdx(wiki.getDepthIdx());
//        wikiSnapShot.setContentsMarkup(wiki.getContentsMarkup());
//        wikiSnapShot.setContents(wiki.getContents());
//        wikiSnapShot.setLock(wiki.isLock());
//        wikiSnapShot.setPasswd(wiki.getPasswd());
//        wikiSnapShot.setUserNick(wiki.getUserNick());
//        wikiSnapShot.setUserId(wiki.getUserId());
//        wikiSnapShot.setViewCount(wiki.getViewCount());
//        wikiSnapShot.setLikeCount(wiki.getLikeCount());
//        wikiSnapShot.setReportCount(wiki.getReportCount());
//        wikiSnapShot.setFixed(wiki.isFixed());
//        wikiSnapShot.setWikiUrl(wiki.getWikiUrl());
//        wikiSnapShot.setCurrentIp(wiki.getCurrentIp());
//        wikiSnapShot.setEditReason(wiki.getEditReason());
//        wikiSnapShot.setRevision(wiki.getRevision());
//        wikiSnapShot.setDeleted(wiki.isDeleted());
//        wikiSnapShot.setInsertDate(wiki.getInsertDate());
//        wikiSnapShot.setUpdateDate(wiki.getUpdateDate());
//        wikiSnapShot.setRevisionActionType(revisionActionTypeEnum);


        return wikiSnapShotRepository.save(wikiSnapShot);
    }

}
