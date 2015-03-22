package com.libqa.web.service;

import com.libqa.web.domain.Space;

import java.util.List;

/**
 * Created by yion on 2015. 3. 1..
 */
public interface SpaceService {

	Space save(Space space);

	List<Space> findAllByCondition(boolean isDeleted);

	Space findOne(Integer spaceId);

}
