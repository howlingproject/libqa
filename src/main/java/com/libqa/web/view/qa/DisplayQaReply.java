package com.libqa.web.view.qa;

import com.libqa.web.domain.QaReply;
import lombok.Getter;

import java.util.List;

/**
 * Created by yong on 2015-06-21.
 *
 * @author yong
 */
@Getter
public class DisplayQaReply {
    private QaReply qaReply;
    private List<DisplayQaReply> qaReplies;
    private boolean selfRecommend;
    private boolean selfNonrecommend;

    public DisplayQaReply(QaReply qaReply, List<DisplayQaReply> qaReplies){
        this.qaReply = qaReply;
        this.qaReplies = qaReplies;
    }

    public DisplayQaReply(QaReply qaReply, List<DisplayQaReply> qaReplies, boolean selfRecommend, boolean selfNonrecommend){
        this.qaReply = qaReply;
        this.qaReplies = qaReplies;
        this.selfRecommend = selfRecommend;
        this.selfNonrecommend = selfNonrecommend;
    }

}
