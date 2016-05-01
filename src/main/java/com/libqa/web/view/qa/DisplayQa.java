package com.libqa.web.view.qa;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.collect.Lists;
import com.libqa.web.domain.*;
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

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private Date insertDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private Date updateDate;

    private String userNick;
    private String title;
    private String contents;
    private String contentsMarkup;
    private int viewCount = 0;
    private int recommendCount = 0;
    private boolean selfRecommend = false;
    private boolean selfNonrecommend = false;
    private int nonrecommendCount = 0;
    private List<Keyword> keywords = Lists.newArrayList();
    private int replyCnt;
    private String userImage;
    private List<QaRecommend> qaRecommendList;
    private List<QaRecommend> qaNonRecommendList;

    public DisplayQa(QaContent qaContent, List<QaRecommend> qaRecommendList, List<QaRecommend> qaNonRecommendList, Integer sesstionUserId) {
        this.qaId = qaContent.getQaId();
        this.insertDate = qaContent.getInsertDate();
        this.updateDate = qaContent.getUpdateDate();
        this.userNick = qaContent.getUserNick();
        this.title = qaContent.getTitle();
        this.contents = qaContent.getContents();
        this.contentsMarkup = qaContent.getContentsMarkup();
        this.viewCount = qaContent.getViewCount();
        this.recommendCount = qaContent.getRecommendCount();
        this.nonrecommendCount = qaContent.getNonrecommendCount();
        this.qaRecommendList = qaRecommendList;
        this.qaNonRecommendList = qaNonRecommendList;
        setSelfRecommend(qaContent, sesstionUserId);
    }

    public DisplayQa(QaContent qaContent, User writer, List<Keyword> keywords, List<QaReply> qaReplies) {
        this.qaId = qaContent.getQaId();
        this.insertDate = qaContent.getInsertDate();
        this.updateDate = qaContent.getUpdateDate();
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

    private void setSelfRecommend(QaContent qaContent, Integer userId){
        List<QaRecommend> qaRecommendList = qaContent.getQaRecommends();
        for(QaRecommend qaRecommend : qaRecommendList){
            if( (qaRecommend.getUserId() == userId) && (qaRecommend.getIsCommend() == true) ){
                this.selfRecommend = true;
            }
            if( (qaRecommend.getUserId() == userId) && (qaRecommend.getIsCommend() == false) ){
                this.selfNonrecommend = true;
            }
        }
    }
}
