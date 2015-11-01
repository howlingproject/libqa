package com.libqa.web.service;

import com.libqa.application.enums.ActivityType;
import com.libqa.web.domain.Keyword;
import com.libqa.web.domain.Wiki;
import com.libqa.web.domain.WikiReply;
import com.libqa.web.repository.WikiReplyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        return wikiReplyRepository.countByWikiId(wikiId);

    }

    @Override
    public WikiReply save(WikiReply reply) {
        WikiReply result = wikiReplyRepository.save(reply);
        return result;
    }

}
