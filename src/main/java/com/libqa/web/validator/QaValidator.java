package com.libqa.web.validator;

import com.libqa.web.domain.QaContent;
import com.libqa.web.domain.QaReply;
import com.libqa.web.domain.User;
import com.libqa.web.service.qa.QaRecommendService;
import com.libqa.web.service.qa.QaReplyService;
import com.libqa.web.service.qa.QaService;
import com.libqa.web.service.qa.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by yong on 2016-04-10.
 *
 * @author yong
 */
@Component
public class QaValidator {

    @Autowired
    QaService qaService;

    @Autowired
    QaReplyService qaReplyService;

    @Autowired
    QaRecommendService qaRecommendService;

    @Autowired
    VoteService voteService;

    public boolean isNotMatchUser(Integer qaId, User user){
        QaContent originQaContent = qaService.findByQaId(qaId, false);
        if(user.isNotMatchUser(originQaContent.getUserId())){
            return true;
        }
        return false;
    }

    public boolean checkQaContentDelete(Integer qaId){
        // 답변, 추천, 비추천 check
        if(isExistReply(qaId)){
            return true;
        }
        if(isExistRecommend(qaId)){
            return true;
        }
        return false;
    }

    public boolean isExistReply(Integer qaId) {
        if(qaReplyService.getCountByQaId(qaId) == 0){
            return false;
        } else {
            return true;
        }
    }

    public boolean isExistRecommend(Integer qaId){
        if(qaRecommendService.getCountByQaId(qaId) == 0){
            return false;
        } else {
            return true;
        }
    }

    public boolean isExistVote(Integer qaId){
        if(isExistReply(qaId)){
            List<QaReply> qaReplys = qaReplyService.findByQaId(qaId);
            boolean returnObj = false;
            for(QaReply qaReply : qaReplys){
                if(voteService.getCountByReplyId(qaReply.getReplyId()) != 0){
                    returnObj = true;
                    break;
                }
            }
            return returnObj;
        } else {
            return false;
        }
    }

}
