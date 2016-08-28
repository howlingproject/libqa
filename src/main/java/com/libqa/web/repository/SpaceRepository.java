package com.libqa.web.repository;

import com.libqa.web.domain.Space;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by yion on 15. 2. 8..
 */
public interface SpaceRepository extends JpaRepository<Space, Integer> {


    List<Space> findAllByIsDeleted(Sort orders, boolean isDeleted);

    Page<Space> findPagingByIsDeleted(Pageable pageable, boolean isDeleted);

    @Query(value = "SELECT s.* FROM space s " +
            "WHERE s.is_deleted = 0 " +
            "   AND (s.title LIKE CONCAT('%',:searchValue,'%') OR s.description_markup LIKE CONCAT('%',:searchValue,'%') ) " +
            "ORDER BY s.space_id DESC", nativeQuery = true)
    List<Space> findAllBySearchValue(@Param("searchValue") String searchValue);
}
