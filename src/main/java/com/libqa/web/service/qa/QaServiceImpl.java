package com.libqa.web.service.qa;

import com.libqa.application.dto.QaDto;
import com.libqa.application.enums.DayType;
import com.libqa.application.enums.KeywordType;
import com.libqa.web.domain.Keyword;
import com.libqa.web.domain.QaContent;
import com.libqa.web.domain.QaFile;
import com.libqa.web.domain.User;
import com.libqa.web.repository.QaContentRepository;
import com.libqa.web.service.common.KeywordService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by yong on 2015-03-08.
 *
 * @author yong
 */
@Slf4j
@Service
public class QaServiceImpl implements QaService {

    @Autowired
    QaContentRepository qaRepository;

    @Autowired
    KeywordService keywordService;

    @Autowired
    QaFileService qaFileService;

    @Override
    @Transactional
    public QaContent saveWithKeyword(QaContent paramQaContent, QaFile qaFiles, Keyword keyword, User user){
        QaContent newQaContent = new QaContent();
        try {

            // TODO List 차후 로그인으로 변경
            paramQaContent.setUserId(user.getUserId());
            paramQaContent.setUserNick(user.getUserNick());
            paramQaContent.setInsertUserId(user.getUserId());
            paramQaContent.setInsertDate(new Date());

            newQaContent = save(paramQaContent);
            moveQaFilesToProductAndSave(newQaContent.getQaId(), qaFiles);
            saveKeywordAndList(newQaContent.getQaId(), keyword.getKeywords(), keyword.getDeleteKeywords());
        }catch(Exception e){
            log.error("### moveQaFilesToProductAndSave Exception = {}", e);
            throw new RuntimeException("moveQaFilesToProductAndSave Exception");
        }
        return newQaContent;
    }

    @Override
    public boolean deleteWithKeyword(Integer qaId) {
        boolean result = false;
        try{
            delete(qaId);
            // TODO List reply, file, recommend, keyword, keywordList 처리 확인
            result = true;
        } catch (Exception e) {
            log.error("삭제시 오류 발생", e);
            result = false;
        }
        return result;
    }

    @Override
    public void saveIsReplyed(Integer qaId, boolean b) {
        QaContent targetQaContent = findByQaId(qaId, false);
//        entityManager.getTransaction().begin();
        // TODO List 차후 로그인으로 변경
        targetQaContent.setReplyed(b);
        targetQaContent.setUpdateUserId(1);
        targetQaContent.setUpdateDate(new Date());
        qaRepository.flush();
    }

    @Override
    public QaContent updateWithKeyword(QaContent requestQaContent, QaFile requestQaFiles, Keyword requestKeywords,  User user) {
        QaContent updateQaContent = new QaContent();

        requestQaContent.setUpdateUserId(user.getUserId());
        requestQaContent.setUpdateDate(new Date());

        try {
            updateQaContent = save(requestQaContent);
            moveQaFilesToProductAndSave(updateQaContent.getQaId(), requestQaFiles);
            saveKeywordAndList(updateQaContent.getQaId(), requestKeywords.getKeywords(), requestKeywords.getDeleteKeywords());
        }catch(Exception e){
            log.error("### moveQaFilesToProductAndSave Exception = {}", e);
            throw new RuntimeException("moveQaFilesToProductAndSave Exception");
        }
        return updateQaContent;
    }

    @Override
    public List<QaContent> findByUserId(Integer userId) {
        return qaRepository.findByUserIdAndIsDeleted(userId, false);
    }

    @Override
    public List<QaContent> findByQaIdIn(List<Integer> qaIds) {
        return qaRepository.findByQaIdInAndIsDeletedOrderByQaIdDesc(qaIds, false);
    }

    @Override
    @Transactional
    public QaContent view(Integer qaId) {
        QaContent qaContent = findByQaId(qaId, false);
        qaContent.setViewCount(qaContent.getViewCount() + 1);
        return qaContent;
    }

    void moveQaFilesToProductAndSave(Integer qaId, QaFile qaFiles) {
        qaFileService.moveQaFilesToProductAndSave(qaId, qaFiles);
    }

    void saveKeywordAndList(Integer qaId, String keywords, String deleteKeywords) {
        if (qaId != 0) {
            // todo deleteKeywords String 배열로 만들어서 keywordService에서 공통으로 입력, 수정, 삭제 처리 구현

            String[] keywordArrays = new String[0];
            String[] deleteKeywordArrays = new String[0];
            if(keywords != null){
                keywordArrays = keywords.split(",");
            }
            if(deleteKeywords != null){
                deleteKeywordArrays = deleteKeywords.split(",");
            }
//            int keywordListSize = deleteKeywords.size();
//            String [] keywordArrays = (String[]) qaContentInstance.getKeyword().toArray(new String[keywordListSize]);
            log.info(" keywordArrays : {}", keywordArrays.length);
            log.info(" deleteKeywordArrays : {}", deleteKeywordArrays.length);
            if (keywordArrays.length > 0 || deleteKeywordArrays.length > 0) {
                keywordService.saveKeywordAndList(keywordArrays, deleteKeywordArrays, KeywordType.QA, qaId);
            }
        }
    }

    /*
    public void saveKeywordAndList(Integer qaId, St) {
        if (qaContentInstance.getKeyword() != null) {
            int keywordListSize = qaContentInstance.getKeyword().size();
            String [] keywordArrays = (String[]) qaContentInstance.getKeyword().toArray(new String[keywordListSize]);
            keywordService.saveKeywordAndList(keywordArrays, KeywordTypeEnum.QA, newQaContent.getQaId());
        }
    }
    */

    public QaContent save(QaContent qaContent) {
        return qaRepository.save(qaContent);
    }

    private void delete(Integer qaId) {
        QaContent targetQaContent = findByQaId(qaId, false);
//        entityManager.getTransaction().begin();
        // TODO List 차후 로그인으로 변경
        targetQaContent.setDeleted(true);
        targetQaContent.setUpdateUserId(1);
        targetQaContent.setUpdateDate(new Date());
        qaRepository.flush();
//        entityManager.getTransaction().commit();

    }

    @Override
    public QaContent findByQaId(Integer qaId, boolean isDeleted) {
        return qaRepository.findOneByQaIdAndIsDeleted(qaId, isDeleted);
    }

    @Override
    public List<QaContent> findByIsReplyedAndDayType(QaDto qaDto) {
        boolean isDeleted = false;
        boolean isReplyed = false;
        Date today = new Date();
        List<QaContent> returnQaContentObj = new ArrayList<>();
        try {
            Date fromDate = getFromDate(qaDto.getDayType());
            List<Integer> qaIds = getQaIdByKeyword(qaDto.getKeywordName());
            if ("Y".equals(qaDto.getWaitReply())) {
                returnQaContentObj = findRecentList(qaIds, isReplyed, fromDate, today, isDeleted);
            } else {
                returnQaContentObj = findWaitList(qaIds, fromDate, today, isDeleted);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return returnQaContentObj;
    }

    public List<QaContent> findRecentList(List<Integer> qaIds, boolean isReplyed, Date fromDate, Date today, boolean isDeleted){
        List<QaContent> recentList = new ArrayList<>();
        if(fromDate == null){
            recentList = qaRepository.findAllByQaIdInAndIsReplyedAndIsDeleted(qaIds, isReplyed, isDeleted);
        } else {
            recentList = qaRepository.findAllByQaIdInAndIsReplyedAndInsertDateBetweenAndIsDeleted(qaIds, isReplyed, fromDate, today, isDeleted);
        }
        return recentList;
    }

    public List<QaContent> findWaitList(List<Integer> qaIds, Date fromDate, Date today, boolean isDeleted){
        return qaRepository.findAllByQaIdInAndInsertDateBetweenAndIsDeleted(qaIds, fromDate, today, isDeleted);
    }

    public Date getFromDate(String dayType) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date now = dateFormat.parse(dateFormat.format(new Date()));
        Date returnDate;
        if(DayType.WEEK.getCode().equals(dayType)){
            returnDate = DateUtils.addDays(now, -7);
        } else if(DayType.ALL.getCode().equals(dayType)){
            returnDate = null;
        } else{
            returnDate = now;
        }
        return returnDate;
    }

    public List<Integer> getQaIdByKeyword(String keywordName){
        boolean isDeleted = false;
        List<Integer> qaIds = new ArrayList();
        List<Keyword> keywords = keywordService.findAllByKeywordTypeAndKeywordNameAndIsDeleted(KeywordType.QA, keywordName, isDeleted);
        for(Keyword keyword : keywords){
            qaIds.add(keyword.getQaId());
        }
        return qaIds;
    }


}
