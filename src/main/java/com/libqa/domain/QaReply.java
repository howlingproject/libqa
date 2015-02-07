package com.libqa.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by yong on 15. 2. 1..
 */
@Entity
@Data
@EqualsAndHashCode
public class QaReply {
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer replyId;

    @ManyToOne
    @JoinColumn(name="qaId", referencedColumnName="qaId", nullable=false)
    private QaContent qaContent;

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

    @Column(nullable = false, columnDefinition = "int default 0")
    private Integer voteUpCount;

    @Column(nullable = false, columnDefinition = "int default 0")
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

    @OneToMany(mappedBy = "qaReply", fetch = FetchType.LAZY)
    private List<Vote> votes;
}
