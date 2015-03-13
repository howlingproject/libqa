package com.libqa.web.service;

import com.libqa.application.enums.SpaceViewEnum;
import com.libqa.domain.Space;

import java.util.List;

/**
 * Created by yion on 2015. 3. 1..
 */
public interface SpaceService {

    Space save(Space space);

    List<Space> findByLayout(SpaceViewEnum left);
}
