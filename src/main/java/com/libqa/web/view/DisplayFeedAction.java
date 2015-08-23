package com.libqa.web.view;

import com.libqa.web.domain.FeedAction;
import lombok.Getter;

@Getter
public class DisplayFeedAction {
    private boolean hasViewerAction = false;
    private Integer count = 0;

    public DisplayFeedAction(FeedAction feedAction, Integer count) {
        this.hasViewerAction = feedAction.isNotCanceled();
        this.count = count;
    }
}
