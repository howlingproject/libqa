package com.libqa.web.service;

import com.libqa.web.domain.FeedReply;
import com.libqa.web.repository.FeedReplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeedReplyService {
    @Autowired
    private FeedReplyRepository feedReplyRepository;
    
    public void save(FeedReply feedReply){
        feedReplyRepository.save(feedReply);
    }
    
}
