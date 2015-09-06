package com.libqa.web.view;

import lombok.Getter;

@Getter
public class DisplayFeedAction {
    private Integer count = 0;
    private boolean hasViewer = false;

    public DisplayFeedAction(Integer count, boolean hasViewer) {
        this.count = count;
        this.hasViewer = hasViewer;
    }
}
