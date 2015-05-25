package com.libqa.web.view;

import com.google.common.collect.Lists;
import com.libqa.web.domain.Feed;
import com.libqa.web.domain.FeedFile;
import com.libqa.web.domain.FeedReply;
import lombok.Getter;

import java.util.Date;
import java.util.List;

@Getter
public class DisplayFeed {
    private final long feedId;
    private String feedContent;
    private String userNick;
    private Date insertDate;
    private List<FeedFile> files = Lists.newArrayList();
    private List<FeedFile> images = Lists.newArrayList();
    private List<FeedReply> replies = Lists.newArrayList();

    public DisplayFeed(Feed feed) {
        this.feedId = feed.getFeedId();
        this.userNick = feed.getUserNick();
        this.insertDate = feed.getInsertDate();
        this.feedContent = feed.getFeedContent();
        this.replies = feed.getFeedReplies();

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

