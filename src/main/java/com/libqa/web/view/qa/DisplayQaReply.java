package com.libqa.web.view.qa;

import com.libqa.web.domain.QaReply;
import com.libqa.web.domain.User;
import lombok.Getter;

import java.util.List;

/**
 * Created by yong on 2015-06-21.
 *
 * @author yong
 */
@Getter
public class DisplayQaReply {
    private String userNick;
    private String userImage;
    private QaReply qaReply;
    private List<DisplayQaReply> qaReplies;
    private boolean selfRecommend;
    private boolean selfNonrecommend;
    private boolean isWriter;

//    public DisplayQaReply(QaReply qaReply, List<DisplayQaReply> qaReplies){
//        this.qaReply = qaReply;
//        this.qaReplies = qaReplies;
//    }

    public DisplayQaReply(QaReply qaReply, List<DisplayQaReply> qaReplies, boolean selfRecommend, boolean selfNonrecommend, User writer, boolean isWriter){
        this.qaReply = qaReply;
        this.qaReplies = qaReplies;
        this.selfRecommend = selfRecommend;
        this.selfNonrecommend = selfNonrecommend;
        this.userNick = writer.getUserNick();
        this.userImage = writer.getUserImage();
        this.isWriter = isWriter;
    }

}
