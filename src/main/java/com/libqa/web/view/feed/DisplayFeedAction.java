package com.libqa.web.view.feed;

import lombok.Getter;

@Getter
public class DisplayFeedAction {
    private boolean hasViewer = false;
    private Integer count = 0;

    public DisplayFeedAction(boolean hasViewer, Integer count) {
        this.hasViewer = hasViewer;
        this.count = count;
    }
}
