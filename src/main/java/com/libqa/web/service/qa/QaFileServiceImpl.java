package com.libqa.web.service.qa;

import com.libqa.application.dto.FileDto;
import com.libqa.application.framework.ResponseData;
import com.libqa.application.util.FileHandler;
import com.libqa.application.util.StringUtil;
import com.libqa.web.controller.CommonController;
import com.libqa.web.domain.QaFile;
import com.libqa.web.repository.QaFileRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.ServletContext;

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

    public QaFile findByQaFileId(Integer qaFileId){
        return qaFileRepository.findOne(qaFileId);
    }

    @Override
    @Transactional
    public boolean moveQaFilesToProductAndSave(Integer qaId, QaFile paramQaFiles) {
        boolean result = true;
        try {
            if (paramQaFiles.getRealNames() != null) {
                for (int qaFileIndex = 0; qaFileIndex < paramQaFiles.getRealNames().size(); qaFileIndex++) {
                    ResponseData<?> resultFile = new ResponseData<>();
                    if(paramQaFiles.getFileIds().isEmpty()) {
                        resultFile = moveQaFileToProduct(paramQaFiles, qaFileIndex);
                    }
                    if (1 == resultFile.getResultCode()) {
                        makeQaFileInstance(qaId, resultFile);
                    }
                }
            }
            if(paramQaFiles.getDeleteFiles() != null){
                for(int deleteFileIndex = 0; deleteFileIndex < paramQaFiles.getDeleteFiles().size(); deleteFileIndex++){
                    deleteQaFile(Integer.valueOf((String)paramQaFiles.getDeleteFiles().get(deleteFileIndex)));
                }
            }
        }catch(Exception e){
            result = false;
            log.error("### moveQaFilesToProductAndSave Exception = {}", e);
        }
        return result;
    }

    public ResponseData<?> moveQaFileToProduct(QaFile paramQaFiles, int qaFileIndex) {
        ResponseData<?> resultFile = new ResponseData<>();
        FileDto fileDto = new FileDto();
        try {
            fileDto.setRealName((java.lang.String) paramQaFiles.getRealNames().get(qaFileIndex));
            fileDto.setSavedName((java.lang.String) paramQaFiles.getSavedNames().get(qaFileIndex));
            fileDto.setFilePath((java.lang.String) paramQaFiles.getFilePaths().get(qaFileIndex));
            fileDto.setRootPath(StringUtil.defaultString(servletContext.getRealPath(FileHandler.SEPARATOR)));
            fileDto.setFileSize(Integer.parseInt((java.lang.String)paramQaFiles.getFileSizes().get(qaFileIndex)));
            fileDto.setFileExtendType((java.lang.String) paramQaFiles.getFileTypes().get(qaFileIndex));
            resultFile = commonController.moveFileToProduct(fileDto);
        }catch(Exception e){
            log.error("### moveQaFileToProduct Exception = {}", e);
        }
        return resultFile;
    }

    public void makeQaFileInstance(Integer qaId, ResponseData<?> resultFile) {
        FileDto resultFileDto = (FileDto) resultFile.getData();
        QaFile qaFile = new QaFile();
        qaFile.setQaId(qaId);
        qaFile.setRealName(resultFileDto.getRealName());
        qaFile.setSavedName(resultFileDto.getSavedName());
        qaFile.setFilePath(resultFileDto.getFilePath());
        qaFile.setFileSize((int) resultFileDto.getFileSize());
        qaFile.setFileType(resultFileDto.getFileExtendType());
        qaFile.setUserId(1);
        saveQaFile(qaFile);
    }

    public void saveQaFile(QaFile qaFileInstance) {
        qaFileRepository.save(qaFileInstance);
    }

    public void deleteQaFile(Integer qaFileId){
        QaFile qaFile = findByQaFileId(qaFileId);
        qaFile.setDeleted(true);
    }
}
