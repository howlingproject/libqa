package com.libqa.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class WikiFile{

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer fileId;

    @ManyToOne
    @JoinColumn(referencedColumnName="wikiId",  nullable = false)
    private Wiki wiki;

    @Temporal(TemporalType.TIMESTAMP)
    private Date insertDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updateDate;

    @Column(nullable = false, length = 100)
    private String realName;

    @Column(nullable = false, length = 100)
    private String saveName;

    @Column(nullable = false)
    private String path;

    @Column(length = 50)
    private Integer fileSize;

    @Column
    private String fileType;

    @Column(columnDefinition = "TINYINT(1) DEFAULT 0")
    private boolean isDeleted;

    @Column(nullable = false)
    private Integer userId;


}
