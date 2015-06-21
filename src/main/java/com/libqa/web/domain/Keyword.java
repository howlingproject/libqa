package com.libqa.web.domain;

import com.libqa.application.enums.KeywordTypeEnum;
import lombok.Data;
import lombok.Getter;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by yong on 15. 2. 1..
 */
@Entity
@Data
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
    @Column(nullable = false, length = 20)
    private KeywordTypeEnum keywordType;

    @Column(columnDefinition = "TINYINT(1) DEFAULT 0")
    private boolean isDeleted;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date insertDate;

    @Temporal(TemporalType.DATE)
    private Date updateDate;

    private int keywordGroupCount;

}
