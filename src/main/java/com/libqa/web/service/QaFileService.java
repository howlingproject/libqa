package com.libqa.web.service;

import com.libqa.web.domain.QaContent;
import com.libqa.web.domain.QaFile;

/**
 * Created by yong on 2015-03-28.
 *
 * @author yong
 */
public interface QaFileService {
    boolean saveQaFileAndFileMove(QaContent qaContent);

    QaFile save(QaFile qaFile);

    void removeFile(QaFile qaFile);


}
