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
    private final Integer feedId;
    private String feedContent;
    private String userNick;
    private Date insertDate;
    private int likeCount;
    private int claimCount;                      
    private List<DisplayFeedReply> replies = Lists.newArrayList();
    private List<FeedFile> files = Lists.newArrayList();
    private List<FeedFile> images = Lists.newArrayList();
    
    public DisplayFeed(Feed feed) {
        this.feedId = feed.getFeedId();
        this.userNick = feed.getUserNick();
        this.insertDate = feed.getInsertDate();
        this.likeCount = feed.getLikeCount();
        this.claimCount = feed.getClaimCount();
        this.feedContent = feed.getFeedContent();
        setReplies(feed.getFeedReplies());
        setFiles(feed.getFeedFiles());
    }

    private void setReplies(List<FeedReply> feedReplies) {
        for (FeedReply feedReply : feedReplies) {
          this.replies.add(new DisplayFeedReply(feedId, feedReply));
        }
    }

    private void setFiles(List<FeedFile> feedFiles) {
        for (FeedFile each : feedFiles) {
            if (each.isFileType()) {
                this.files.add(each);
            }

            if (each.isImageType()) {
                this.images.add(each);
            }
        }
    }
}

