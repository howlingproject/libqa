package com.libqa.web.domain;

import com.libqa.application.enums.FeedActionType;
import com.libqa.application.enums.FeedThreadType;
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
    private FeedThreadType feedThreadType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private FeedActionType feedActionType;

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

    public boolean isNotCanceled() {
        return !this.isCanceled;
    }

    public void cancelByUser(User user) {
        this.setCanceled(true);
        this.setUpdateDate(new Date());
        this.setUpdateUserId(user.getUserId());
    }

    public static FeedAction notYetCreated() {
        return EMPTY;
    }

    public boolean isActed() {
        return this != EMPTY;
    }

}
