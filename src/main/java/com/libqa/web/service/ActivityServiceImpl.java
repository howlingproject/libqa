package com.libqa.web.service;

import com.libqa.web.domain.Activity;
import com.libqa.web.repository.ActivityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author : yion
 * @Date : 2015. 6. 28.
 * @Description :
 */
@Slf4j
@Service
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    private ActivityRepository activityRepository;

    @Override
    public Activity saveActivity(Activity activity) {
        return activityRepository.save(activity);
    }

    @Override
    public List<Activity> findBySpaceId(Integer spaceId) {
        return activityRepository.findBySpaceId(spaceId);
    }

    @Override
    public List<Activity> findByWikiId(Integer wikiId) {
        return activityRepository.findByWikiId(wikiId);
    }


}
