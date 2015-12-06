package com.libqa.web.service.common;

import com.libqa.web.domain.Activity;

import java.util.List;

/**
 * @Author : yion
 * @Date : 2015. 6. 28.
 * @Description :
 */
public interface ActivityService {

    Activity saveActivity(Activity activity, String title);

    List<Activity> findBySpaceId(Integer spaceId);

    List<Activity> findByWikiId(Integer wikiId);
}
