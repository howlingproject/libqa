package com.libqa.web.view;

import com.libqa.application.enums.FeedActionType;
import com.libqa.application.util.LoggedUser;
import com.libqa.web.domain.FeedAction;
import com.libqa.web.domain.User;
import com.libqa.web.service.FeedActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DisplayFeedActionBuilder {

    @Autowired
    private LoggedUser loggedUser;
    @Autowired
    private FeedActionService feedActionService;

    public DisplayFeedAction build(int viewCount, int feedActorId, FeedActionType feedActionType) {
        User user = loggedUser.getDummyUser();
        FeedAction feedAction = feedActionService.getFeedAction(feedActorId, user.getUserId(), feedActionType);
        return new DisplayFeedAction(viewCount, feedAction.isActed());
    }
}
