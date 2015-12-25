package com.libqa.web.domain;

import com.libqa.application.enums.KeywordType;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by yong on 15. 2. 1..
 */
@Entity
@Data
@Table(indexes = {
        @Index(name="IDX_KEYWORD_SPACE_ID",columnList = "spaceId"),
        @Index(name="IDX_KEYWORD_QA_ID",columnList = "qaId"),
        @Index(name="IDX_KEYWORD_WIKI_ID",columnList = "wikiId")
})
public class Keyword {
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer keywordId;

    @Column(nullable = true)
    private Integer qaId;

    @Column(nullable = true)
    private Integer wikiId;

    @Column(nullable = true)
    private Integer spaceId;

    @Column(nullable = false, length = 40)
    private String keywordName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private KeywordType keywordType;

    @Column(columnDefinition = "TINYINT(1) DEFAULT 0")
    private boolean isDeleted;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date insertDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updateDate;

    private int keywordGroupCount;

    @Transient
    private String keywords;

    @Transient
    private String deleteKeywords;
}
