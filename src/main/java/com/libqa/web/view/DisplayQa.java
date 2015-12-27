package com.libqa.web.view;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.collect.Lists;
import com.libqa.web.domain.Keyword;
import com.libqa.web.domain.QaContent;
import com.libqa.web.domain.QaReply;
import com.libqa.web.domain.User;
import lombok.Getter;

import java.util.Date;
import java.util.List;

/**
 * Created by yong on 2015-05-15.
 *
 * @author yong
 */
@Getter
public class DisplayQa {
    private Integer qaId;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private Date insertDate;

    private String userNick;
    private String title;
    private String contents;
    private String contentsMarkup;
    private int viewCount = 0;
    private int recommendCount = 0;
    private QaContent qaContent;
    private List<Keyword> keywords = Lists.newArrayList();
    private int replyCnt;
    private String userImage;

    public DisplayQa(QaContent qaContent, User writer, List<Keyword> keywords, List<QaReply> qaReplies){
        this.qaId = qaContent.getQaId();
        this.insertDate = qaContent.getInsertDate();
        this.userNick = qaContent.getUserNick();
        this.title = qaContent.getTitle();
        this.contents = qaContent.getContents();
        this.contentsMarkup = qaContent.getContentsMarkup();
        this.viewCount = qaContent.getViewCount();
        this.recommendCount = qaContent.getRecommendCount();
        this.keywords = keywords;
        this.replyCnt = qaReplies.size();
        this.userImage = writer.getUserImage();
    }
}
