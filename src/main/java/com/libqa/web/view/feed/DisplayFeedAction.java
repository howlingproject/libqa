package com.libqa.web.view.feed;

import lombok.Getter;

@Getter
public class DisplayFeedAction {
    private boolean hasAction = false;
    private Integer count = 0;

    public DisplayFeedAction(boolean hasAction, Integer count) {
        this.hasAction = hasAction;
        this.count = count;
    }
}
