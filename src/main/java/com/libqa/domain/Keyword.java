package com.libqa.domain;

import com.libqa.application.enums.KeywordTypeEnum;
import lombok.Data;
import org.springframework.data.annotation.Id;

import javax.persistence.*;

/**
 * Created by yong on 15. 2. 1..
 */
@Entity
@Data
public class Keyword {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
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

    @Column(nullable = false)
    private boolean isDeleted;

    @Temporal(TemporalType.TIMESTAMP)
    private Data insertDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Data updateDate;

}
