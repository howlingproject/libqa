package com.libqa.web.service;

import com.libqa.application.enums.KeywordTypeEnum;
import com.libqa.web.domain.QaContent;
import com.libqa.web.domain.QaFile;
import com.libqa.web.repository.QaContentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public QaContent saveWithKeyword(QaContent qaContentInstance) {
        QaContent qaContent;
        try {
            qaContentInstance.setUserId(1);
            qaContentInstance.setUserNick("용퓌");
            qaContentInstance.setInsertUserId(1);
            qaContentInstance.setInsertDate(new Date());
            qaContent = qaRepository.save(qaContentInstance);

            for(int i=0; i < qaContentInstance.getRealName().size(); i++){
                QaFile qaFileInstance = new QaFile();
                qaFileInstance.setQaContent(qaContentInstance);
                qaFileInstance.setRealName((String) qaContentInstance.getRealName().get(i));
                qaFileInstance.setSavedName((String) qaContentInstance.getSavedName().get(i));
                qaFileInstance.setFilePath((String) qaContentInstance.getFilePath().get(i));
                qaFileInstance.setFileSize(Integer.parseInt((String) qaContentInstance.getFileSize().get(i)));
                qaFileInstance.setFileType((String) qaContentInstance.getFileType().get(i));
                qaFileInstance.setUserId(1);
                qaFileService.saveQaFile(qaFileInstance);
            }

            String [] keywordArrays = (String[]) qaContentInstance.getKeyword().toArray(new String[qaContentInstance.getKeyword().size()]);
            if (keywordArrays.length > 0) {
                keywordService.saveKeywordAndList(keywordArrays, KeywordTypeEnum.QA, qaContent.getQaId());
            }
        }catch(Exception e){
            log.info(e.toString());
            throw new RuntimeException("제발제발!!!!!");
        }
        return qaContent;
    }

    @Override
    public QaContent findByQaId(Integer qaId, boolean isDeleted) {
        return qaRepository.findOneByQaIdAndIsDeleted(qaId, isDeleted);
    }

}
