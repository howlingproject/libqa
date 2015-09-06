package com.libqa.web.repository;

import com.libqa.web.domain.Wiki;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by yong on 15. 2. 8..
 */
public interface WikiRepository extends JpaRepository<Wiki, Integer> {

    List<Wiki> findAllByIsDeleted(Sort orders, boolean isDeleted);

    List<Wiki> findAllByUserIdAndIsDeleted(Integer userId, Sort orders, boolean isDeleted);

    List<Wiki> findBySpaceIdAndIsDeleted(Integer spaceId, boolean isDeleted);

    List<Wiki> findAllBySpaceIdAndIsDeleted(Integer spaceId, boolean isDeleted, Sort sort);

    List<Wiki> findAllByWikiIdInAndIsDeleted(List<Integer> wikiIds, Sort sort, boolean isDeleted);

    List<Wiki> findAllByContentsMarkupContainingAndIsDeleted(String contentsMarkup, Sort sort, boolean isDeleted);

    List<Wiki> findAllByParentsIdAndIsDeleted(Integer parentId, boolean isDeleted);

    Wiki findOneByWikiIdAndIsDeleted(Integer wikiId, boolean isDeleted);
}
