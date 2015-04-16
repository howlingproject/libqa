package com.libqa.web.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by yong on 15. 2. 1..
 */
@Data
@Entity
@Table(name = "qa_reply")
@EqualsAndHashCode
public class QaReply {

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer replyId;

    @JsonBackReference
    @Column(nullable = false)
    private Integer qaId;

    @Column(nullable = false)
    private Integer parentsId;

    @Column(nullable = false, columnDefinition = "int default 0")
    private Integer orderIdx;

    @Column(nullable = false, columnDefinition = "int default 0")
    private Integer depthIdx;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false, columnDefinition = "Text")
    private String contents;

    @Column(nullable = false, columnDefinition = "Text")
    private String contentsMarkup;

    @Column(nullable = false)
    private Integer userId;

    @Column(nullable = false, length = 40)
    private String userNick;

    @Column(nullable = false, columnDefinition = "int default 0")
    private int voteUpCount = 0;

    @Column(nullable = false, columnDefinition = "int default 0")
    private int voteDownCount = 0;

    @Column(nullable = false, columnDefinition = "TINYINT(1) DEFAULT 0")
    private boolean isChoice;

    @Column(nullable = false, columnDefinition = "TINYINT(1) DEFAULT 0")
    private boolean isDeleted;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date insertDate;

    @Temporal(TemporalType.DATE)
    private Date updateDate;

    @Column(nullable = false)
    private Integer insertUserId;

    @Column
    private Integer updateUserId;

    @OneToMany(mappedBy = "replyId", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Vote> votes;
}
