package com.libqa.web.service;

import com.libqa.application.dto.QaDto;
import com.libqa.application.enums.DayTypeEnum;
import com.libqa.application.enums.KeywordTypeEnum;
import com.libqa.web.domain.Keyword;
import com.libqa.web.domain.QaContent;
import com.libqa.web.domain.QaFile;
import com.libqa.web.repository.QaContentRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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

    @PersistenceContext
    EntityManager entityManager;

    @Override
    @Transactional
    public QaContent saveWithKeyword(QaContent qaContent, QaFile qaFiles){
        QaContent newQaContent = new QaContent();
        try {
            newQaContent = save(qaContent);
            moveQaFilesToProductAndSave(newQaContent.getQaId(), qaFiles);
            saveKeywordAndList(newQaContent.getQaId(), qaContent.getKeywords());
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

            // TODO List reply, file, recommand, keyword, keywordList 처리 확인
            result = true;
        } catch (Exception e) {
            log.error("삭제시 오류 발생", e);
            result = false;
        }
        return result;
    }

    void moveQaFilesToProductAndSave(Integer qaId, QaFile qaFiles) {
        qaFileService.moveQaFilesToProductAndSave(qaId, qaFiles);
    }

    void saveKeywordAndList(Integer qaId, String keywords) {
        if (qaId != 0) {
            String[] keywordArrays = keywords.split(",");
            log.info(" keywordArrays : {}", keywordArrays.length);
            if (keywordArrays.length > 0) {
                keywordService.saveKeywordAndList(keywordArrays, KeywordTypeEnum.QA, qaId);
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
    @Transactional(readOnly = false)
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
        List<QaContent> waitList = new ArrayList<>();
        return waitList = qaRepository.findAllByQaIdInAndInsertDateBetweenAndIsDeleted(qaIds, fromDate, today, isDeleted);
    }

    public Date getFromDate(String dayType){
        Date now = new Date();
        Date returnDate;
        if(DayTypeEnum.WEEK.getCode().equals(dayType)){
            returnDate = DateUtils.addDays(now, -7);
        } else if(DayTypeEnum.ALL.getCode().equals(dayType)){
            returnDate = null;
        } else{
            returnDate = now;
        }
        return returnDate;
    }

    public List<Integer> getQaIdByKeyword(String keywordName){
        boolean isDeleted = false;
        List<Integer> qaIds = new ArrayList();
        List<Keyword> keywords = keywordService.findAllByKeywordTypeAndKeywordNameAndIsDeleted(KeywordTypeEnum.QA, keywordName, isDeleted);
        for(Keyword keyword : keywords){
            qaIds.add(keyword.getQaId());
        }
        return qaIds;
    }

}
