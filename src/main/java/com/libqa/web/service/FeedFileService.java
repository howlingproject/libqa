package com.libqa.web.service;

import com.libqa.web.domain.FeedFile;
import com.libqa.web.repository.FeedFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeedFileService {
    @Autowired
    private FeedFileRepository repository;

    public void save(FeedFile feedFile) {
        repository.save(feedFile);
    }

    public void deleteByFeedId(Long feedId) {
        FeedFile feedFile = repository.findByFeedId(feedId);
        if(feedFile == null) {
            return;
        }
        
        feedFile.setDeleted(true);
        repository.save(feedFile);          // TODO 영속성 체크
    }
}
