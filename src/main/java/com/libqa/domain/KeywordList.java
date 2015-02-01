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
public class KeywordList {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Integer keywordListId;

    @Column(nullable = false)
    private String keywordName;

    @Column(nullable = false)
    private Integer keywordCount;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private KeywordTypeEnum keywordType;

    @Column(nullable = false, columnDefinition="TINYINT(1) DEFAULT 0")
    private boolean isDeleted;
}
