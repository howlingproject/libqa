package com.libqa.domain;

import lombok.Data;

import javax.persistence.*;
/**
 * Created by yong on 15. 2. 1..
 */
@Entity
@Data
public class QaFile {
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer fileId;

    @Column(nullable = false)
    private Integer qaId;

    @Column(nullable = false, length = 40)
    private String realName;

    @Column(nullable = false, length = 80)
    private String savedName;

    @Column(nullable = false, length = 80)
    private String filePath;

    @Column(nullable = false)
    private Integer fileSize;

    @Column(nullable = true, length = 10)
    private String fileType;

    @Column(nullable = false, columnDefinition="TINYINT(1) DEFAULT 0")
    private String isDeleted;

    @Column(nullable = true)
    private Integer userId;
}
