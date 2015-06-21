package com.libqa.web.repository;

import com.libqa.web.domain.Wiki;
import com.libqa.web.domain.WikiReply;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by yong on 15. 2. 8..
 */
public interface WikiReplyRepository extends JpaRepository<WikiReply, Integer>{

    int countByWiki(Wiki wiki);

}
