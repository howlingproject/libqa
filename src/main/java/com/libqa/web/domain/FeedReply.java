package com.libqa.web.domain;

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
public class FeedReply {
    @Id
    @Column(name = "feedReplyId", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long feedReplyId;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String feedReplyContent;

    @Column(columnDefinition = "TINYINT(1) DEFAULT 0")
    private boolean isDeleted;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date insertDate;

    @Temporal(TemporalType.DATE)
    private Date updateDate;

    @Column(nullable = false)
    private Integer userId;

    @Column(nullable = false, length = 40)
    private String userNick;

    @Column(nullable = false)
    private Integer insertUserId;

    @Column
    private Integer updateUserId;

    @ManyToOne
    @JoinColumn(name = "feedId", referencedColumnName = "feedId", nullable = false)
    private Feed feed;
}

