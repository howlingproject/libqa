package com.libqa.domain;

import com.libqa.application.enums.FeedLikeTypeEnum;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by sjune on 2015-02-01.
 *
 * @author sjune
 */
@Data
@Entity
public class FeedLikeUser {
                                
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long feedLikeUserId;
    
    @Column(nullable = false)
    private Long feedId;
    
    @Column(nullable = false)
    private Long replyId;
    
    @Enumerated(EnumType.STRING)
    private FeedLikeTypeEnum feedLikeType;
    
    @Column(columnDefinition = "TINYINT(1) DEFAULT 0")
    private boolean isCanceled;
    
    @Temporal(TemporalType.DATE)
    private Date insertDate;

    @Temporal(TemporalType.DATE)
    private Date updateDate;

    @Column(nullable = false)
    private Integer userId;

    @Column(nullable = false)
    private String userNick;

    @Column(nullable = false)
    private Integer insertUserId;

    @Column(length = 40)
    private Integer updateUserId;
}
