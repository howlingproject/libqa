package com.libqa.web.service;

import com.libqa.web.domain.Wiki;
import com.libqa.web.domain.WikiFile;
import com.libqa.web.repository.WikiFileRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by songanji on 2015. 4. 19..
 */
@Service
@Slf4j
public class WikiFileServiceImpl implements WikiFileService {
    @Autowired
    WikiFileRepository wikiFileRepository;

    @Override
    public boolean saveWikiFileAndList(WikiFile wikiFile) {
        wikiFileRepository.save(wikiFile);
        return true;
    }
}
