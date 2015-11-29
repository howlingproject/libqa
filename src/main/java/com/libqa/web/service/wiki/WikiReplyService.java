package com.libqa.web.service.wiki;

import com.libqa.web.domain.WikiReply;

/**
 * Created by songanji on 2015. 4. 26..
 */
public interface WikiReplyService {
    int countByWiki(Integer wikiId);
    WikiReply save(WikiReply wikiReply);
}
