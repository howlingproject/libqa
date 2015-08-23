package com.libqa.web.repository;

import com.libqa.web.domain.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by yong on 15. 2. 8..
 */
public interface VoteRepository extends JpaRepository<Vote, Long> {
    Vote findByReplyIdAndUserIdAndIsCancel(Integer replyId, Integer userId, boolean isCancel);

    List<Vote> findByReplyIdAndUserIdAndIsVoteAndIsCancel(Integer replyId, int userId, boolean isVoted, boolean isCanceled);
}
