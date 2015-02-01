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
public class Vote {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Integer replyId;

    @Column(nullable = false)
    private Integer userId;

    @Column(nullable = false)
    private String userNick;

    @Column(nullable = false, columnDefinition = "TINYINT(1) DEFAULT 0")
    private boolean isVote;

    @Column(nullable = false, columnDefinition = "TINYINT(1) DEFAULT 0")
    private boolean isCancel;

    @Temporal(TemporalType.DATE)
    private Date insertDate;

    @Temporal(TemporalType.DATE)
    private Date updateDate;
}
