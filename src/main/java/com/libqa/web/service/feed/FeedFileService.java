package com.libqa.web.service.feed;

import com.libqa.application.util.FileHandler;
import com.libqa.web.domain.FeedFile;
import com.libqa.web.repository.FeedFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.libqa.application.util.FileHandler.*;

@Service
public class FeedFileService {
    @Autowired
    private FileHandler fileHandler;
    @Autowired
    private FeedFileRepository repository;

    /**
     * feedFile 을 저장한다.
     *
     * @param feedFile
     */
    public void save(FeedFile feedFile) {
        repository.save(feedFile);
    }

    /**
     * feedFileId로 feedFile 을 조회한다.
     *
     * @param feedFileId
     * @return FeedFile
     */
    public FeedFile getByFeedFileId(Integer feedFileId) {
        return repository.findOne(feedFileId);
    }

    /**
     * feedFile 을 제거한다.
     *
     * @param feedFile
     */
    @Transactional
    public void delete(FeedFile feedFile) {
        String fileFullPath = feedFile.getFilePath() + SEPARATOR + feedFile.getSavedName();
        fileHandler.delete(fileFullPath);
        feedFile.setDeleted(true);
    }
}
