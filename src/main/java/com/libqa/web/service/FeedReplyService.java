package com.libqa.web.service;

import com.libqa.application.util.LoggedUser;
import com.libqa.web.domain.FeedReply;
import com.libqa.web.repository.FeedReplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class FeedReplyService {

    @Autowired
    private LoggedUser loggedUser;
    @Autowired
    private FeedReplyRepository feedReplyRepository;

    public void save(FeedReply feedReply) {
//        User user = loggedUser.get(); // TODO 개발 완료후 로그인 기능 추가할 예정.

        feedReply.setInsertDate(new Date());
//        feedReply.setUserId(user.getUserId());
//        feedReply.setUserNick(user.getUserNick());
//        feedReply.setInsertUserId(user.getUserId());
        feedReply.setUserId(1234);
        feedReply.setUserNick("testerNick");
        feedReply.setInsertUserId(1234);
        feedReplyRepository.save(feedReply);
    }

    public void delete(Long feedReplyId) {
        FeedReply feedReply = feedReplyRepository.findOne(feedReplyId);
        deleteIfExists(feedReply);
    }

    public void deleteByFeedId(Long feedId) {
        FeedReply feedReply = feedReplyRepository.findByFeedId(feedId);
        deleteIfExists(feedReply);
    }

    private void deleteIfExists(FeedReply feedReply) {
        if (feedReply == null) {
            return;
        }
        feedReply.setDeleted(true);
        feedReplyRepository.save(feedReply);
    }

}
