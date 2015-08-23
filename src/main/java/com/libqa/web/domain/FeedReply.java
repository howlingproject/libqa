package com.libqa.web.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(indexes = {@Index(columnList = "isDeleted")})
@EqualsAndHashCode(of = "feedReplyId")
@ToString
public class FeedReply {
    @Id
    @Column(name = "feedReplyId", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer feedReplyId;

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

    @Column
    private Integer likeCount = 0;

    @Column
    private Integer claimCount = 0;

    @Column(nullable = false)
    private Integer insertUserId;

    @Column
    private Integer updateUserId;
    
    @Transient
    public void increaseLikeCount() {
        this.likeCount++;
    }

    @Transient
    public void decreaseLikeCount() {
        this.likeCount--;
    }
    
    @Transient
    public void increaseClaimCount() {
        this.claimCount++;
    }
    
    @Transient
    public void decreaseClaimCount() {
        this.claimCount--;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "feedId", referencedColumnName = "feedId")
    @JsonIgnore
    private Feed feed;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "feedReplyId", referencedColumnName = "feedReplyId")
    private List<FeedAction> feedActions;
}

