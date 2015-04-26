package com.libqa.web.service;

import com.libqa.application.dto.FileDto;
import com.libqa.application.framework.ResponseData;
import com.libqa.application.util.FileUtil;
import com.libqa.application.util.StringUtil;
import com.libqa.web.controller.CommonController;
import com.libqa.web.domain.QaContent;
import com.libqa.web.domain.QaFile;
import com.libqa.web.repository.QaFileRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletContext;
import javax.transaction.Transactional;

/**
 * Created by yong on 2015-03-28.
 *
 * @author yong
 */
@Slf4j
@Service
public class QaFileServiceImpl implements QaFileService {

    @Autowired
    QaFileRepository qaFileRepository;

    @Autowired
    ServletContext servletContext;

    @Autowired
    CommonController commonController;

    @Override
    @Transactional
    public boolean saveQaFileAndFileMove(QaContent qaContentInstance) {
        boolean result = true;
        try {
            //qaFileMove(qaContentInstance);
        } catch (Exception e) {
            e.printStackTrace();
            result = false;
        }
        return result;
    }

    public void saveQaFile(QaFile qaFileInstance) {
            qaFileRepository.save(qaFileInstance);
    }

    /*
    public void qaFileMove(QaContent qaContentInstance) throws Exception {
        if(qaContentInstance.getRealName() != null) {
            for (int i = 0; i < qaContentInstance.getRealName().size(); i++) {
                FileDto fileDto = new FileDto();
                fileDto.setRealName((String) qaContentInstance.getRealName().get(i));
                fileDto.setSavedName((String) qaContentInstance.getSavedName().get(i));
                fileDto.setFilePath((String) qaContentInstance.getFilePath().get(i));
                fileDto.setRootPath(StringUtil.defaultString(servletContext.getRealPath(FileUtil.SEPARATOR)));
                fileDto.setFileSize(Integer.parseInt((String) qaContentInstance.getFileSize().get(i)));
                fileDto.setFileExtendType((String) qaContentInstance.getFileType().get(i));
                ResponseData<?> resultFile = commonController.moveFileToProduct(fileDto);
                if (1 == resultFile.getResultCode()) {
                    makeQaFileInstance(qaContentInstance, resultFile);
                }
            }
        }
    }
    */

    public void makeQaFileInstance(QaContent qaContentInstance, ResponseData<?> resultFile) {
        FileDto resultFileDto = (FileDto) resultFile.getData();
        QaFile qaFileInstance = new QaFile();
        qaFileInstance.setQaId(qaContentInstance.getQaId());
        qaFileInstance.setRealName(resultFileDto.getRealName());
        qaFileInstance.setSavedName(resultFileDto.getSavedName());
        qaFileInstance.setFilePath(resultFileDto.getFilePath());
        qaFileInstance.setFileSize((int) resultFileDto.getFileSize());
        qaFileInstance.setFileType(resultFileDto.getFileExtendType());
        qaFileInstance.setUserId(1);
        saveQaFile(qaFileInstance);
    }
}
