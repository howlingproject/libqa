package com.libqa.domain;

import com.libqa.application.enums.SharedContentsTypeEnum;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class Feed {
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer feedId;

    @Column
    private Integer sharedResponseId;

    @Column
    @Enumerated(EnumType.STRING)
    private SharedContentsTypeEnum sharedContentsType;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String feedContent;

    @Column(length = 80)
    private String feedUrl;

    @Column
    private Integer likeCount;

    @Column
    private Integer claimCount;

    @Column(columnDefinition = "TINYINT(1) DEFAULT 0")
    private boolean isShared;

    @Column(columnDefinition = "TINYINT(1) DEFAULT 0")
    private boolean isPrivate;

    @Column(columnDefinition = "TINYINT(1) DEFAULT 0")
    private boolean isSharedFb;

    @Column(columnDefinition = "TINYINT(1) DEFAULT 0")
    private boolean isSharedTw;

    @Column(columnDefinition = "TINYINT(1) DEFAULT 0")
    private boolean isSharedGp;
    
    @Column(columnDefinition = "TINYINT(1) DEFAULT 0")
    private boolean isDeleted;

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
