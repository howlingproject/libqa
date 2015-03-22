package com.libqa.web.service;

import com.libqa.application.enums.SpaceViewEnum;
import com.libqa.application.util.PageUtil;
import com.libqa.web.domain.Space;
import com.libqa.web.repository.SpaceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by yion on 2015. 3. 1..
 */
@Slf4j
@Service
public class SpaceServiceImpl implements SpaceService {

	@Autowired
	private SpaceRepository spaceRepository;

	@Override
	public Space save(Space space) {
		return spaceRepository.save(space);
	}

	@Override
	public List<Space> findAllByCondition(boolean isDeleted) {
		List<Space> spaceList = null;
		try {
			spaceList = spaceRepository.findAllByIsDeleted(PageUtil.sortId("DESC", "spaceId"), isDeleted);
		} catch (Exception e) {
			log.error("## Space Sort Error");
			e.printStackTrace();
		}
		return spaceList;
	}

	@Override
	public Space findOne(Integer spaceId) {
		return spaceRepository.findOne(spaceId);
	}


}
