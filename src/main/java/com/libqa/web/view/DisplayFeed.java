package com.libqa.web.view;

import com.google.common.collect.Lists;
import com.libqa.web.domain.Feed;
import com.libqa.web.domain.FeedFile;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Getter
public class DisplayFeed {
    private Feed feed;
    private List<FeedFile> files = Lists.newArrayList();
    private List<FeedFile> images = Lists.newArrayList();

    public DisplayFeed(Feed feed) {
        this.feed = feed;
        for (FeedFile each : feed.getFeedFiles()) {
            if (each.isFileType()) {
                this.files.add(each);
            }

            if (each.isImageType()) {
                this.images.add(each);
            }
        }
    }
}

