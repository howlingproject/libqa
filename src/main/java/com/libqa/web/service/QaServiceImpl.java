package com.libqa.web.service;

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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
    public QaContent saveWithKeyword(QaContent qaContentInstance, QaFile qaFile) {
        QaContent newQaContent;
        try {
            newQaContent = qaContentSave(qaContentInstance);
            saveQaFileAndFileMove(qaContentInstance);
            saveKeywordAndList(qaContentInstance, newQaContent);
        }catch(Exception e){
            log.info(e.toString());
            throw new RuntimeException("제발제발!!!!!");
        }
        return newQaContent;
    }

    public void saveKeywordAndList(QaContent qaContentInstance, QaContent newQaContent) {
        if (qaContentInstance.getKeyword() != null) {
            int keywordListSize = qaContentInstance.getKeyword().size();
            String [] keywordArrays = (String[]) qaContentInstance.getKeyword().toArray(new String[keywordListSize]);
            keywordService.saveKeywordAndList(keywordArrays, KeywordTypeEnum.QA, newQaContent.getQaId());
        }
    }

    public boolean saveQaFileAndFileMove(QaContent qaContentInstance) {
        return qaFileService.saveQaFileAndFileMove(qaContentInstance);
    }

    public QaContent qaContentSave(QaContent qaContentInstance) {
        qaContentInstance.setUserId(1);
        qaContentInstance.setUserNick("용퓌");
        qaContentInstance.setInsertUserId(1);
        qaContentInstance.setInsertDate(new Date());
        return qaRepository.save(qaContentInstance);
    }

    @Override
    @Transactional(readOnly = false)
    public QaContent findByQaId(Integer qaId, boolean isDeleted) {
        return qaRepository.findOneByQaIdAndIsDeleted(qaId, isDeleted);
    }

    @Override
    public List<QaContent> findByIsReplyedAndDaytype(Map<String, String> params) {
        boolean isDeleted = false;
        Date today = new Date();
        List<QaContent> returnQaContentObj = new ArrayList<>();
        try {
            Date fromDate = searchDayType(params.get("dayType"));
            List<Integer> qaIds = getQaIdByKeyword(params);
            if ("Y".equals(params.get("waitReply"))) {
                returnQaContentObj = qaRepository.findAllByQaIdInAndIsReplyedAndInsertDateBetweenAndIsDeleted(qaIds, false, fromDate, today, isDeleted);
            } else {
                returnQaContentObj = qaRepository.findAllByQaIdInAndInsertDateBetweenAndIsDeleted(qaIds, fromDate, today, isDeleted);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return returnQaContentObj;
    }

    public Date searchDayType(String dayType){
        Date now = new Date();
        Date returnDate;
        if(DayTypeEnum.WEEK.equals(dayType)){
            returnDate = DateUtils.addDays(now, -7);
        } else if(DayTypeEnum.ALL.equals(dayType)){
            returnDate = null;
        } else{
            returnDate = now;
        }
        return returnDate;
    }

    public List<Integer> getQaIdByKeyword(Map<String, String> params){
        boolean isDeleted = false;
        List<Integer> qaIds = new ArrayList();
        List<Keyword> keywords = keywordService.findAllByKeywordTypeAndKeywordNameAndIsDeleted(KeywordTypeEnum.QA, params.get("keywordName"), isDeleted);
        for(Keyword keyword : keywords){
            qaIds.add(keyword.getQaId());
        }
        return qaIds;
    }

}
