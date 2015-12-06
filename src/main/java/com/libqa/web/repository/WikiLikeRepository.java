package com.libqa.web.repository;

import com.libqa.web.domain.WikiLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by yong on 15. 2. 8..
 */
public interface WikiLikeRepository extends JpaRepository<WikiLike, Integer> {

    WikiLike findOneByUserIdAndWikiId(Integer userId, Integer wikiId);

    WikiLike findOneByUserIdAndReplyId(Integer userId, Integer wikiId);
}