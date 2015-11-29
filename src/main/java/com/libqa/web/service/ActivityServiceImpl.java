package com.libqa.web.service;

import com.libqa.application.enums.ActivityType;
import com.libqa.application.util.PageUtil;
import com.libqa.web.domain.Activity;
import com.libqa.web.repository.*;
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

    @Autowired
    private SpaceRepository spaceRepository;

    @Autowired
    private WikiRepository wikiRepository;

    @Autowired
    private WikiReplyRepository replyRepository;

    @Autowired
    private QaContentRepository qaContentRepository;

    @Autowired
    private QaReplyRepository qaReplyRepository;


    @Override
    public Activity saveActivity(Activity activity, String title) {
        String desc = bindActivityType(activity, title);
        activity.setActivityDesc(desc);

        return activityRepository.save(activity);
    }

    public String bindActivityType(Activity activity, String title) {
        String desc = "";
        switch (activity.getActivityType()) {
            case CREATE_SPACE:
                desc = activity.getUserNick() + "님이 [" + title + "] " + ActivityType.CREATE_SPACE.getCode();
                break;

            case UPDATE_SPACE:
                desc = activity.getUserNick() + "님이 [" + title + "] " + ActivityType.UPDATE_SPACE.getCode();
                break;

            case ADD_SPACE_FAVORITE:
                desc = activity.getUserNick() + "님이 [" + title + "] " + ActivityType.ADD_SPACE_FAVORITE.getCode();
                break;

            case INSERT_WIKI:
                desc = activity.getUserNick() + "님이 [" + title + "] " + ActivityType.INSERT_WIKI.getCode();
                break;

            case UPDATE_WIKI:
                desc = activity.getUserNick() + "님이 [" + title + "] " + ActivityType.UPDATE_WIKI.getCode();
                break;

            case INSERT_REPLY_WIKI:
                desc = activity.getUserNick() + "님이 [" + title + "] " + ActivityType.INSERT_REPLY_WIKI.getCode();
                break;

            case ADD_WIKI_FAVORITE:
                desc = activity.getUserNick() + "님이 [" + title + "] " + ActivityType.ADD_WIKI_FAVORITE.getCode();
                break;

            case INSERT_REPLY_QA:
                desc = activity.getUserNick() + "님이 [" + title + "] " + ActivityType.INSERT_REPLY_QA.getCode();
                break;

            case ADD_VOTE_YES:
                desc = activity.getUserNick() + "님이 [" + title + "] " + ActivityType.ADD_VOTE_YES.getCode();
                break;

            case ADD_VOTE_NO:
                desc = activity.getUserNick() + "님이 [" + title + "] " + ActivityType.ADD_VOTE_NO.getCode();
                break;


            case ADD_REPLY_RECOMMEND:
                desc = activity.getUserNick() + "님이 [" + title + "] " + ActivityType.ADD_REPLY_RECOMMEND.getCode();
                break;
        }
        return desc;
    }

    @Override
    public List<Activity> findBySpaceId(Integer spaceId) {

        return activityRepository.findBySpaceIdAndIsDeleted(spaceId, false, PageUtil.sortId("DESC", "insertDate"));
    }

    @Override
    public List<Activity> findByWikiId(Integer wikiId) {
        return activityRepository.findByWikiId(wikiId);
    }


}
