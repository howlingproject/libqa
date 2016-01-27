package com.libqa.web.view.feed;

import lombok.Getter;

@Getter
public class DisplayFeedAction {
    private boolean hasActor = false;
    private Integer count = 0;

    public DisplayFeedAction(boolean hasActor, Integer count) {
        this.hasActor = hasActor;
        this.count = count;
    }
}
