package com.libqa.domain;

import com.libqa.application.enums.KeywordTypeEnum;
import lombok.Data;
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

    @Column(nullable = false)
    private Integer qaId;

    @Column(nullable = false)
    private Integer wikiId;

    @Column(nullable = false)
    private Integer spaceId;

    @Column(nullable = false)
    private String keywordName;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private KeywordTypeEnum keywordType;

    @Column(columnDefinition = "TINYINT(1) DEFAULT 0")
    private boolean isDeleted;

    @Temporal(TemporalType.TIMESTAMP)
    private Date insertDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updateDate;

}
