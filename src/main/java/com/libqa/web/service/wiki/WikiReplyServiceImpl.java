package com.libqa.web.service.wiki;

import com.google.common.base.MoreObjects;
import com.libqa.web.domain.Wiki;
import com.libqa.web.domain.WikiReply;
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

    @Autowired
    WikiService wikiService;


    @Override
    public int countByWiki(Integer wikiId) {
        return wikiReplyRepository.countByWikiId(wikiId);
    }

    @Override
    public WikiReply findById(Integer replyId) {
        return wikiReplyRepository.findOne(replyId);
    }

    @Override
    public WikiReply update(WikiReply reply) {
        WikiReply result = wikiReplyRepository.save(reply);
        return result;
    }

    @Override
    public WikiReply save(WikiReply reply) {
        WikiReply result = wikiReplyRepository.save(reply);

        Wiki wiki = wikiService.findById(reply.getWikiId());
        wiki.setReplyCount( MoreObjects.firstNonNull(wiki.getReplyCount(), 0) + 1 );
        wikiService.save(wiki);

        return result;
    }

}
