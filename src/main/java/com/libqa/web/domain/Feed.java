package com.libqa.web.domain;

import com.libqa.application.enums.SocialChannelTypeEnum;
import lombok.*;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Builder
@Entity
@Table(indexes = {@Index(columnList = "isDeleted")})
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "feedId")
@ToString(exclude = {"feedReplies", "feedFiles", "feedLikeUsers"})
public class Feed {
    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long feedId;

    @Column(nullable = true, length = 80)
    private String sharedResponseId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = true, length = 20)
    private SocialChannelTypeEnum sharedContentsType;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String feedContent;

    @Column(length = 255)
    private String feedUrl;

    @Column
    private Integer likeCount = 0;

    @Column
    private Integer claimCount = 0;

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

    @OneToMany(mappedBy = "feed", fetch = FetchType.LAZY)
    @Where(clause = "is_deleted = 0")
    private List<FeedReply> feedReplies;

    @OneToMany(mappedBy = "feed", fetch = FetchType.LAZY)
    @Where(clause = "is_deleted = 0")
    private List<FeedFile> feedFiles;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "feedId", referencedColumnName = "feedId")
    private List<FeedAction> feedActions;
}
