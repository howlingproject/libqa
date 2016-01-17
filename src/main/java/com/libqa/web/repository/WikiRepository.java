package com.libqa.web.repository;

import com.libqa.web.domain.Wiki;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by yong on 15. 2. 8..
 */
public interface WikiRepository extends JpaRepository<Wiki, Integer>, CrudRepository<Wiki, Integer> {

    List<Wiki> findAllByIsDeleted(Sort orders, boolean isDeleted);

    List<Wiki> findAllByIsDeleted(boolean isDeleted, Pageable pageable);

    List<Wiki> findAllByUserIdAndIsDeleted(Integer userId, boolean isDeleted, Pageable pageable);

    List<Wiki> findBySpaceIdAndIsDeleted(Integer spaceId, boolean isDeleted);

    List<Wiki> findAllBySpaceIdAndIsDeleted(Integer spaceId, boolean isDeleted, Pageable pageable);

    List<Wiki> findAllByWikiIdInAndIsDeleted(List<Integer> wikiIds, boolean isDeleted, Pageable pageable);

    List<Wiki> findAllByContentsMarkupContainingAndIsDeleted(String contentsMarkup, boolean isDeleted, Pageable pageable);

    List<Wiki> findAllByParentsIdAndIsDeleted(Integer parentId, boolean isDeleted);

    Wiki findOneByWikiIdAndIsDeleted(Integer wikiId, boolean isDeleted);

    Long countByIsDeleted(boolean isDeleted);

    List<Wiki> findSpaceWikiUpdateByIsDeleted(boolean isDeleted, Pageable pageable);

    List<Wiki> findAllByIsDeletedFalse(Pageable pageable);
}