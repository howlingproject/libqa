package com.libqa.web.service.wiki;

import com.google.common.base.MoreObjects;
import com.libqa.application.enums.ActivityType;
import com.libqa.application.enums.KeywordType;
import com.libqa.application.enums.WikiRevisionActionType;
import com.libqa.application.util.PageUtil;
import com.libqa.web.domain.*;
import com.libqa.web.repository.KeywordRepository;
import com.libqa.web.repository.WikiLikeRepository;
import com.libqa.web.repository.WikiRepository;
import com.libqa.web.repository.WikiSnapShotRepository;
import com.libqa.web.service.user.UserService;
import com.libqa.web.view.wiki.DisplayWiki;
import com.libqa.web.service.common.ActivityService;
import com.libqa.web.service.common.KeywordService;
import com.libqa.web.view.wiki.DisplayWikiLike;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
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

    @Autowired
    private ActivityService activityService;

    @Autowired
    private WikiLikeRepository wikiLikeRepository;

    @Autowired
    private UserService userService;

    final boolean isDeleted = false;

    @Override
    public Wiki save(Wiki wiki) {
        return wikiRepository.save(wiki);
    }

    @Override
    @Transactional
    public Wiki saveWithKeyword(Wiki wiki, Keyword keyword) {
        Wiki result = save(wiki);
        saveWikiFileAndList(result);
        saveKeywordAndList(keyword, result);
        saveWikiActivity(result, ActivityType.INSERT_WIKI);
        return result;
    }

    private void saveWikiActivity(Wiki saveWiki, ActivityType ActivityType) {

        Activity activity = new Activity();
        activity.setInsertDate(new Date());
        activity.setDeleted(false);

        activity.setActivityType(ActivityType);
        activity.setActivityDesc(ActivityType.getCode());
        activity.setActivityKeyword(KeywordType.WIKI);
        activity.setUserId(saveWiki.getUserId());
        activity.setUserNick(saveWiki.getUserNick());
        activity.setWikiId(saveWiki.getWikiId());
        activityService.saveActivity(activity, saveWiki.getTitle());
    }

    @Override
    @Transactional
    public Wiki updateWithKeyword(Wiki wiki, Keyword keyword, WikiRevisionActionType revisionActionTypeEnum) {
        WikiSnapShot wikiSnapShot = new WikiSnapShot();
        BeanUtils.copyProperties(wiki, wikiSnapShot);

        wikiSnapShot.setWiki(wiki);
        wikiSnapShot.setRevisionActionType(revisionActionTypeEnum);
        wikiSnapShotRepository.save(wikiSnapShot);

        Wiki result = save(wiki);

        saveWikiActivity(wiki, ActivityType.UPDATE_WIKI);

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

    private void saveKeywordAndList(Keyword keyword, Wiki result) {
        String[] keywordArrays = new String[0];
        String[] deleteKeywordArrays = new String[0];
        if(keyword.getKeywords() != null){
            keywordArrays = keyword.getKeywords().split(",");
        }
        if(keyword.getDeleteKeywords() != null){
            deleteKeywordArrays = keyword.getDeleteKeywords().split(",");
        }
        log.debug(" keywordArrays : {}", keywordArrays.length);
        if (keywordArrays.length > 0) {
            keywordService.saveKeywordAndList(keywordArrays, deleteKeywordArrays, KeywordType.WIKI, result.getWikiId());
        }
    }

    @Override
//    @Transactional(readOnly = true)
    public Wiki findById(Integer wikiId) {
        return wikiRepository.findOne(wikiId);
    }

    @Override
    public Wiki findByParentId(Integer parentId) {
        if( parentId == null  ){
            return  null;
        }
        return wikiRepository.findOneByWikiIdAndIsDeleted(parentId, isDeleted);
    }

    @Override
    public List<Wiki> findBySubWikiId(Integer wikiId) {
        return wikiRepository.findAllByParentsIdAndIsDeleted(wikiId, isDeleted);
    }

    @Override
    public List<DisplayWiki> findByAllWiki(int page, int size) {
        List<DisplayWiki> resultWiki = new ArrayList<>();
        List<Wiki> list = wikiRepository.findAllByIsDeleted(
                isDeleted
                , PageUtil.sortPageable(page, size, PageUtil.sortId("DESC", "insertDate"))
                );
        if( !CollectionUtils.isEmpty( list )){
            for( Wiki wiki : list ){
                resultWiki.add( new DisplayWiki(
                        wiki
                        , userService.findByUserId( wiki.getUserId() )
                        ,keywordService.findByWikiId(wiki.getWikiId(), false)
                )
                );
            }
        }
        return resultWiki;
    }

    @Override
    public Long countByAllWiki(){
        return wikiRepository.countByIsDeleted(isDeleted);
    }

    @Override
    public List<DisplayWiki> findByBestWiki(int page, int size) {
        List<DisplayWiki> resultWiki = new ArrayList<DisplayWiki>();
        List<Wiki> list = wikiRepository.findAllByIsDeleted(
                isDeleted
                , PageUtil.sortPageable(page, size, PageUtil.sortId("DESC", "likeCount"))
                );

        if( !CollectionUtils.isEmpty( list ) ){
            for( Wiki wiki : list ){
                resultWiki.add( new DisplayWiki(
                                wiki
                                , userService.findByUserId( wiki.getUserId() )
                                ,keywordService.findByWikiId(wiki.getWikiId(), false)
                        )
                );
            }
        }

        return resultWiki;
    }

    @Override
    public List<DisplayWiki> findByRecentWiki(int userId, int page, int size) {
        List<DisplayWiki> resultWiki = new ArrayList<DisplayWiki>();
        List<Wiki> list = wikiRepository.findAllByUserIdAndIsDeleted(
                userId
                , isDeleted
                , PageUtil.sortPageable(
                        page
                        , size
                        , PageUtil.sort(PageUtil.order("DESC", "userId"), PageUtil.order("DESC", "insertDate"))
                    )
                );

        if( !CollectionUtils.isEmpty( list ) ){
            for( Wiki wiki : list ){
                resultWiki.add( new DisplayWiki(
                                wiki
                                , userService.findByUserId( wiki.getUserId() )
                                ,keywordService.findByWikiId(wiki.getWikiId(), false)
                        )
                );
            }
        }

        return resultWiki;
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
                    PageUtil.sortPageable(startIdx, endIdx, PageUtil.sortId("DESC", "updateDate")));
        } catch (Exception e) {
            e.getMessage();
        }

        return wikis;
    }

    @Override
    public List<Wiki> findWikiListByKeyword(String keywordNm, int page, int size) {
        List<Keyword> keywordList = keywordRepository.findAllByKeywordTypeAndKeywordNameAndIsDeleted(KeywordType.WIKI, keywordNm, false);
        List<Integer> wikiIds = getWikiIdByKeyworld(keywordList);

        List<Wiki> list = wikiRepository.findAllByWikiIdInAndIsDeleted(
                wikiIds
                , isDeleted
                , PageUtil.sortPageable(
                        page
                        , size
                        , PageUtil.sortId("DESC", "insertDate")
                    )
                );
        return list;
    }

    @Override
    public List<Wiki> findWikiListByContentsMarkup(String searchText, int page, int size) {
        List<Wiki> list = wikiRepository.findAllByContentsMarkupContainingAndIsDeleted(
                searchText
                , isDeleted
                , PageUtil.sortPageable(
                        page
                        , size
                        , PageUtil.sortId("DESC", "insertDate")
                )
                );
        return list;
    }

    @Override
    public DisplayWikiLike updateLike(WikiLike like) {
        DisplayWikiLike result = new DisplayWikiLike(0, null);
        try{
            WikiLike dupLike = null;
            if( like.getWikiId() != null ){
                dupLike = wikiLikeRepository.findOneByUserIdAndWikiId(like.getUserId(), like.getWikiId());
            }else if( like.getReplyId() != null ){
                dupLike = wikiLikeRepository.findOneByUserIdAndReplyId(like.getUserId(), like.getReplyId());
            }

            if( dupLike == null ){
                if( like.getWikiId() != null ){
                    Wiki wiki = findById(like.getWikiId());
                    wiki.setLikeCount( MoreObjects.firstNonNull(wiki.getLikeCount(), 0)  + 1 );
                    save(wiki);
                }
                wikiLikeRepository.save(like);
                result.setResult(1);
                result.setWikiLike(like);
            }
        }catch(Exception e){
            e.getMessage();
        }
        return result;
    }

    private List<Integer> getWikiIdByKeyworld(List<Keyword> keywords){
        List<Integer> wikiIds = new ArrayList<>();
        for( Keyword keyword : keywords ){
            wikiIds.add(keyword.getWikiId());
        }
        return wikiIds;
    }


    private WikiSnapShot saveSnapShot(Wiki wiki, WikiRevisionActionType revisionActionTypeEnum) {

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

    @Override
    public List<DisplayWiki> findUpdateWikiList(int startIdx, int endIdx) {
        List<DisplayWiki> resultWiki = new ArrayList<DisplayWiki>();
        List<Wiki> list = wikiRepository.findSpaceWikiUpdateByIsDeleted(
                isDeleted
                , PageUtil.sortPageable(startIdx, endIdx, PageUtil.sortId("DESC", "updateDate"))
        );

        if(!CollectionUtils.isEmpty(list)){
            for( Wiki wiki : list ){
                resultWiki.add( new DisplayWiki(wiki));
            }
        }

        return resultWiki;

    }

    @Override
    public List<Wiki> searchRecentlyWikiesByPageSize(Integer pageSize) {
        final Integer startIndex = 0;
        final Sort sort = PageUtil.sortId("DESC", "wikiId");
        PageRequest pageRequest = PageUtil.sortPageable(startIndex, pageSize, sort);
        return wikiRepository.findAllByIsDeletedFalse(pageRequest);
    }
}
