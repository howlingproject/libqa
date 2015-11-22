package com.libqa.web.view;

import com.google.common.collect.Lists;
import com.libqa.application.util.HtmlContentHandler;
import com.libqa.web.domain.Feed;
import com.libqa.web.domain.FeedFile;
import lombok.Getter;

import java.util.List;

@Getter
public class DisplayFeed {
    private final Integer feedId;
    private String feedContent;
    private String userNick;
    private String insertDate;
    private DisplayFeedAction likeFeedAction;
    private DisplayFeedAction claimFeedAction;
    private List<DisplayFeedReply> replies = Lists.newArrayList();
    private List<FeedFile> files = Lists.newArrayList();
    private List<FeedFile> images = Lists.newArrayList();

    public DisplayFeed(Feed feed, DisplayFeedAction likeFeedAction, DisplayFeedAction claimFeedAction, List<DisplayFeedReply> replies) {
        this.feedId = feed.getFeedId();
        this.userNick = feed.getUserNick();
        this.feedContent = parseHtml(feed.getFeedContent());
        this.insertDate = DisplayDateParser.parseForFeed(feed.getInsertDate());
        this.likeFeedAction = likeFeedAction;
        this.claimFeedAction = claimFeedAction;
        this.replies = replies;
        setFeedFiles(feed.getFeedFiles());
    }

    private String parseHtml(String feedContent) {
        return new HtmlContentHandler(feedContent).urlWithLink().nl2br().parse();
    }

    private void setFeedFiles(List<FeedFile> feedFiles) {
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

