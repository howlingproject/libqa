package com.libqa.web.service;

import com.libqa.application.util.PageUtil;
import com.libqa.domain.Space;
import com.libqa.repository.SpaceRepository;
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
	public List<Space> findAll() {
		log.info("@Service ");
		List<Space> spaceList = null;
		try {
			spaceList = spaceRepository.findAllByIsDeleted(PageUtil.sortId("DESC", "spaceId"), false);
		} catch (Exception e) {
			log.error("## Space Sort Error");
			e.printStackTrace();
		}
		return spaceList;
	}

}
