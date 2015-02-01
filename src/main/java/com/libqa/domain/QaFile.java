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

    @Column(nullable = true)
    private String realName;

    @Column(nullable = true)
    private String saveName;

    @Column(nullable = true)
    private String path;

    @Column(nullable = true)
    private Integer fileSize;

    @Column(nullable = true)
    private String fileType;

    @Column(nullable = false, columnDefinition="TINYINT(1) DEFAULT 0")
    private String isDeleted;

    @Column(nullable = true)
    private Integer userId;
}
