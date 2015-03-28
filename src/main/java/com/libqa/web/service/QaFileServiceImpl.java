package com.libqa.web.service;

import com.libqa.web.domain.QaFile;
import com.libqa.web.repository.QaFileRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public void saveQaFile(QaFile qaFileInstance) {
        qaFileRepository.save(qaFileInstance);
    }
}
