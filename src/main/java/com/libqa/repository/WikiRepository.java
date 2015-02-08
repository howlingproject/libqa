package com.libqa.repository;

import com.libqa.domain.Wiki;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by yong on 15. 2. 8..
 */
public interface WikiRepository extends JpaRepository<Wiki, Long> {
}
