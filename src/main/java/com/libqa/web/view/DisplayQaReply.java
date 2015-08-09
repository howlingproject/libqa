package com.libqa.web.view;

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
    private List<QaReply> qaReplies;
//    private boolean

    public DisplayQaReply(QaReply qaReply, List<QaReply> qaReplies){
        this.qaReply = qaReply;
        this.qaReplies = qaReplies;
    }
}
