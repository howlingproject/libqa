package com.libqa.web.service;

import com.libqa.web.domain.Wiki;
import com.libqa.web.repository.WikiReplyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by songanji on 2015. 4. 26..
 */
@Service
@Slf4j
public class WikiReplyServiceImpl implements WikiReplyService {

    @Autowired
    WikiReplyRepository wikiReplyRepository;


    @Override
    public int countByWiki(Integer wikiId) {
        Wiki wiki = new Wiki();
        wiki.setWikiId(wikiId);
        return wikiReplyRepository.countByWiki(wiki);

    }

}
