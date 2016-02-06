package com.libqa.web.domain;

import com.libqa.application.enums.ActionType;
import com.libqa.application.enums.ThreadType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@ToString
@EqualsAndHashCode(of = "feedActionId")
@Table(indexes = {
        @Index(columnList = "isCanceled"),
        @Index(columnList = "feedActorId,userId")
})
public class FeedAction {
    private static final FeedAction EMPTY = new FeedAction();

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer feedActionId;

    @Column(nullable = false)
    private Integer feedActorId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private ThreadType threadType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private ActionType actionType;

    @Column(columnDefinition = "TINYINT(1) DEFAULT 0")
    private boolean isCanceled;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date insertDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updateDate;

    @Column(nullable = false)
    private Integer userId;

    @Column(nullable = false, length = 40)
    private String userNick;

    @Column(nullable = false)
    private Integer insertUserId;

    @Column
    private Integer updateUserId;

    public void cancelByUser(User user) {
        this.setCanceled(true);
        this.setUpdateDate(new Date());
        this.setUpdateUserId(user.getUserId());
    }

    public static FeedAction notYet() {
        return EMPTY;
    }

    public boolean isNotYet() {
        return this == EMPTY;
    }

    public boolean isActed() {
        return !isNotYet();
    }
}
