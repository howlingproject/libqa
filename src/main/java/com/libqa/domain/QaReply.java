package com.libqa.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by yong on 15. 2. 1..
 */
@Entity
@Data
public class QaReply {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Integer replyId;

    @Column(nullable = false)
    private Integer qaId;

    @Column(nullable = true)
    private Integer parentsId;

    @Column(nullable = false)
    private Integer orderIdx;

    @Column(nullable = false)
    private Integer depthIdx;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String contents;

    @Column(nullable = false)
    private Integer userId;

    @Column(nullable = false)
    private String userNick;

    @Column(nullable = true)
    private Integer voteUpCount;

    @Column(nullable = true)
    private Integer voteDownCount;

    @Column(nullable = false, columnDefinition = "TINYINT(1) DEFAULT 0")
    private boolean isChoice;

    @Column(nullable = false, columnDefinition = "TINYINT(1) DEFAULT 0")
    private boolean isDeleted;

    @Temporal(TemporalType.DATE)
    private Date insertDate;

    @Temporal(TemporalType.DATE)
    private Date updateDate;

    @Column(nullable = false)
    private Integer insertUserId;

    @Column(nullable = false)
    private Integer updateUserId;

}
