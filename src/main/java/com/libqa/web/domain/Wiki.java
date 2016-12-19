package com.libqa.web.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by songanji on 2015. 2. 8..
 */
@Entity
@Data
@Slf4j
@ToString(exclude = {"wikiSnapShots"})
@Table(indexes = {
        @Index(name="IDX_WIKI_SPACE_ID",columnList = "spaceId")
})
public class Wiki implements Serializable{

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer wikiId;

    @Column(nullable = false)
    private Integer spaceId;

    @Column
    private Integer parentsId;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false, columnDefinition = "int default 0")
    private Integer orderIdx = 0;

    @Column(nullable = false, columnDefinition = "int default 0")
    private Integer depthIdx = 0;

    @Column
    private Integer groupIdx;

    @Column(columnDefinition = "Text", nullable = false)
    private String contents;

    @Column(columnDefinition = "Text", nullable = false)
    private String contentsMarkup;

    @Column(columnDefinition = "TINYINT(1) DEFAULT 0")
    private boolean isLock = false;

    @Column(length = 40)
    private String passwd;

    @Column(nullable = false, length = 40)
    private String userNick;

    @Column(nullable = false)
    private Integer userId;

    @Column
    private Integer viewCount = 0;

    @Column
    private Integer likeCount = 0;

    @Column
    private Integer replyCount = 0;

    @Column
    private Integer reportCount;

    @Column(columnDefinition = "TINYINT(1) DEFAULT 0")
    private boolean isFixed = false;

    @Column(length = 100)
    private String wikiUrl;

    @Column(length = 16)
    private String currentIp;

    @Column(length = 10)
    private String editReason;

    @Column(length = 10)
    private String revision;

    @Column(columnDefinition = "TINYINT(1) DEFAULT 0")
    boolean isDeleted = false;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    @Temporal(TemporalType.TIMESTAMP)
    private Date insertDate;

    @Column(nullable = false)
    private Integer insertUserId;

    @Column(length = 40)
    private String insertUserNick;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateDate;

    @Column
    private Integer updateUserId;

    @Column(length = 40)
    private String updateUserNick;

    @OneToMany(mappedBy = "wikiId", fetch = FetchType.LAZY)
    @Where(clause = "is_deleted = 0")
    @JsonManagedReference
    private List<WikiFile> wikiFiles;

    @OneToMany(mappedBy = "wiki", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<WikiSnapShot> wikiSnapShots;

    @OneToMany(mappedBy = "wikiId", fetch = FetchType.LAZY)
    @Where(clause = "is_deleted = 0")
    @JsonManagedReference
    private List<WikiReply> wikiReplies;

//    @OneToMany(fetch = FetchType.LAZY)
//    @JoinColumns({
//            @JoinColumn(name = "userId", referencedColumnName = "userId"),
//            @JoinColumn(name = "wikiId", referencedColumnName = "wikiId")
//    })
//
//    private List<WikiLike> wikiLikes;


}