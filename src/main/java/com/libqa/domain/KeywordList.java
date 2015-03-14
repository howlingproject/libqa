package com.libqa.domain;

import com.libqa.application.enums.KeywordTypeEnum;
import lombok.Data;

import javax.persistence.*;

/**
 * Created by yong on 15. 2. 1..
 */
@Entity
@Data
public class KeywordList {

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer keywordListId;

    @Column(nullable = false, length = 40)
    private String keywordName;

    @Column(nullable = false, columnDefinition = "int default 0")
    private Integer keywordCount;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private KeywordTypeEnum keywordType;

    @Column(nullable = false, columnDefinition="TINYINT(1) DEFAULT 0")
    private boolean isDeleted;

    public KeywordList(String keywordName, KeywordTypeEnum keywordType, Integer keywordCount) {
        this.keywordName = keywordName;
        this.keywordType = keywordType;
        this.keywordCount = keywordCount;
    }
}
