package com.libqa.repository;

import com.libqa.domain.QaFile;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by yong on 15. 2. 8..
 */
public interface QaFileRepository extends JpaRepository<QaFile, Long> {
}
