package com.libqa.web.repository;

import com.libqa.web.domain.Wiki;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by yong on 15. 2. 8..
 */
public interface WikiRepository extends JpaRepository<Wiki, Integer> {
}
