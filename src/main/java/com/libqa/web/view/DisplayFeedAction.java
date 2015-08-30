package com.libqa.web.view;

import lombok.Getter;

@Getter
public class DisplayFeedAction {
    private Integer count = 0;
    private boolean hasViewerAction = false;

    public DisplayFeedAction(Integer count, boolean hasViewerAction) {
        this.count = count;
        this.hasViewerAction = hasViewerAction;
    }
}
