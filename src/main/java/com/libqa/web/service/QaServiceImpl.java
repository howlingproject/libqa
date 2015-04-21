package com.libqa.web.service;

import com.libqa.application.enums.KeywordTypeEnum;
import com.libqa.web.domain.QaContent;
import com.libqa.web.domain.QaFile;
import com.libqa.web.repository.QaContentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

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

}
