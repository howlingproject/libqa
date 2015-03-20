package com.libqa.repository;

import com.libqa.domain.Space;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by yong on 15. 2. 8..
 */
public interface SpaceRepository extends JpaRepository<Space, Integer> {


    List<Space> findAllByIsDeleted(Sort orders, boolean isDeleted);

}
