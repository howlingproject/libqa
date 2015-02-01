package com.libqa.domain;

/**
 * Created by songanji on 2015. 2. 1..
 */

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Data
@Table(name = "wikiFile")
class WikiFile implements Serializable {

    @Id
    @Column(name = "fileId", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer fileId;

    @ManyToOne
    @JoinColumn(name = "wikiId", referencedColumnName="wikiId",  nullable = false)
    private Wiki wiki;


    @Temporal(TemporalType.TIMESTAMP)
    Date insertDate;

    @Temporal(TemporalType.TIMESTAMP)
    Date updateDate;


    @Column(name = "realName", nullable = false, length = 100)
    String realName;

    @Column(name = "saveName", nullable = false, length = 100)
    String saveName;

    @Column(name = "path", nullable = false)
    String path;

    @Column(name = "fileSize", length = 50)
    Integer fileSize;

    @Column(name = "fileType")
    String fileType;

    @Column(name = "isDeleted", columnDefinition = "TINYINT(1) DEFAULT 0")
    boolean isDeleted;

    @Column(name = "userId", nullable = false)
    Integer userId;


}
