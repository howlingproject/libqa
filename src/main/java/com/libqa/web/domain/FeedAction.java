package com.libqa.web.domain;

import com.libqa.application.enums.FeedActionTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@ToString
@Table(indexes = {@Index(columnList = "isCanceled"), @Index(columnList = "userId")})
@EqualsAndHashCode(of = "feedActionId")
public class FeedAction {
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer feedActionId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private FeedActionTypeEnum feedActionType;

    @Column(columnDefinition = "TINYINT(1) DEFAULT 0")
    private boolean isCanceled;

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

    @Column
    private Integer feedId;

    @Column
    private Integer feedReplyId;
}
