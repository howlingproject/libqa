package com.libqa.web.view;

import com.google.common.collect.Lists;
import com.libqa.application.util.HtmlContentHandler;
import com.libqa.web.domain.Feed;
import com.libqa.web.domain.FeedFile;
import com.libqa.web.domain.User;
import com.libqa.web.view.feed.DisplayDate;
import com.libqa.web.view.feed.DisplayFeedAction;
import com.libqa.web.view.feed.DisplayFeedReply;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
public class DisplayFeed {
    private Integer feedId;
    private String originFeedContent;
    private String feedContent;
    private Integer userId;
    private String userNick;
    private String userImage;
    private String insertDate;
    private boolean writer;
    private List<FeedFile> files;
    private List<FeedFile> images;

    @Setter
    private boolean isEmpty = false;
    @Setter
    private DisplayFeedAction likeFeedAction;
    @Setter
    private DisplayFeedAction claimFeedAction;
    @Setter
    private List<DisplayFeedReply> replies;

    private DisplayFeed() {
    }

    public DisplayFeed(Feed feed, User user, Boolean isWriter) {
        this.feedId = feed.getFeedId();
        this.userNick = feed.getUserNick();
        this.userImage = user.getUserImage();
        this.originFeedContent = feed.getFeedContent();
        this.feedContent = parseHtml(feed.getFeedContent());
        this.insertDate = DisplayDate.parse(feed.getInsertDate());
        this.writer = isWriter;
        setFeedFiles(feed.getFeedFiles());
    }

    public static DisplayFeed empty() {
        DisplayFeed displayFeed = new DisplayFeed();
        displayFeed.setEmpty(true);
        return displayFeed;
    }

    private String parseHtml(String feedContent) {
        return new HtmlContentHandler(feedContent).urlWithLink().nl2br().parse();
    }

    private void setFeedFiles(List<FeedFile> feedFiles) {
        this.files = Lists.newArrayList();
        this.images = Lists.newArrayList();

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

