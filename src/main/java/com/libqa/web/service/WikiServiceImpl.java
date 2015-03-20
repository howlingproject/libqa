package com.libqa.web.service;

import com.libqa.web.domain.Wiki;
import com.libqa.web.repository.WikiRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by songanji on 2015. 3. 1..
 */
@Service
@Slf4j
public class WikiServiceImpl implements WikiService {
    @Autowired
    WikiRepository wikiRepository;

    @Override
    public Wiki save(Wiki wiki) {
        return wikiRepository.save(wiki);
    }

    @Override
    public Wiki findById(Integer wikiId) {
        return wikiRepository.findOne(wikiId);
    }


}
