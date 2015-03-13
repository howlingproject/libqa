package com.libqa.web.service;

import com.libqa.application.enums.SpaceViewEnum;
import com.libqa.domain.Space;
import com.libqa.repository.SpaceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by yion on 2015. 3. 1..
 */
@Service
@Slf4j
public class SpaceServiceImpl implements SpaceService {

    @Autowired
    private SpaceRepository spaceRepository;

    @Override
    public Space save(Space space) {

        return spaceRepository.save(space);

    }

    @Override
    public List<Space> findByLayout(SpaceViewEnum left) {
        return spaceRepository.findByLayout(left);
    }

}
