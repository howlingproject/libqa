package com.libqa.web.view;

import com.google.common.collect.Lists;
import com.libqa.web.domain.Keyword;
import com.libqa.web.domain.QaContent;
import com.libqa.web.domain.QaReply;
import lombok.Getter;

import java.util.List;

/**
 * Created by yong on 2015-05-15.
 *
 * @author yong
 */
@Getter
public class DisplayQa {
    private QaContent qaContent;
    private List<Keyword> keywords = Lists.newArrayList();
    private int replyCnt;

    public DisplayQa(QaContent qaContent, List<Keyword> keywords, List<QaReply> qaReplies){
        this.qaContent = qaContent;
        this.keywords = keywords;
        this.replyCnt = qaReplies.size();
    }
}
