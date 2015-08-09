package com.libqa.web.repository;

import com.libqa.web.domain.Wiki;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by yong on 15. 2. 8..
 */
public interface WikiRepository extends JpaRepository<Wiki, Integer> {

    List<Wiki> findAllByIsDeleted(Sort orders, boolean isDeleted);

    List<Wiki> findAllByUserIdAndIsDeleted(Integer userId, Sort orders, boolean isDeleted);

    List<Wiki> findBySpaceIdAndIsDeleted(Integer spaceId, boolean isDeleted);

    List<Wiki> findAllBySpaceIdAndIsDeleted(Integer spaceId, boolean isDeleted, Sort sort);

}
