package com.libqa.web.view.feed;

import com.google.common.collect.Lists;
import com.libqa.application.util.FileHandler;
import com.libqa.application.util.HtmlContentHandler;
import com.libqa.web.domain.FeedFile;
import com.libqa.web.domain.FeedThread;
import com.libqa.web.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@NoArgsConstructor
public class DisplayFeed {
    private Integer feedThreadId;
    private String originFeedContent;
    private String feedContent;
    private String userNick;
    private String userImage;
    private String insertDate;
    private boolean writer;
    private List<DisplayFeedFile> files;
    private List<DisplayFeedFile> images;

    @Setter
    private boolean isEmpty = false;
    @Setter
    private DisplayFeedAction likeFeedAction;
    @Setter
    private DisplayFeedAction claimFeedAction;
    @Setter
    private List<DisplayFeedReply> replies;

    DisplayFeed(FeedThread feedThread, User viewer, Boolean isWriter) {
        this.feedThreadId = feedThread.getFeedThreadId();
        this.userNick = feedThread.getUserNick();
        this.userImage = viewer.getUserImage();
        this.originFeedContent = feedThread.getFeedContent();
        this.feedContent = parseHtml(feedThread.getFeedContent());
        this.insertDate = DisplayDate.parse(feedThread.getInsertDate());
        this.writer = isWriter;
        if (feedThread.hasFiles()) {
            setFeedFiles(feedThread.getFeedFiles());
        }
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

        feedFiles.forEach(feedFile -> {
            final String fullPath = feedFile.getFilePath() + FileHandler.SEPARATOR + feedFile.getSavedName();
            DisplayFeedFile displayFeedFile = new DisplayFeedFile(feedFile.getFeedFileId(), feedFile.getRealName(), fullPath);
            if (feedFile.isFileType()) {
                this.files.add(displayFeedFile);
            }

            if (feedFile.isImageType()) {
                this.images.add(displayFeedFile);
            }
        });
    }

}

