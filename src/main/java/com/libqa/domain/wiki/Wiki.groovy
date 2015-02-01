package com.libqa.domain.wiki

import lombok.Data
import lombok.extern.slf4j.Slf4j
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table
import javax.persistence.Temporal
import javax.persistence.TemporalType

@Entity
@Data
@Table(name = "wiki")
@Slf4j
class Wiki implements Serializable{

    @Id
    @Column(name = "spaceId", nullable = false)
    Integer spaceId

    @Id
    @Column(name = "wikiId", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer wikiId

    @Column(name = "parentsId")
    Integer parentsId

    @Column(name = "title", nullable = false, length = 100)
    String title

    @Column(name = "orderIdx")
    Integer orderIdx

    @Column(name = "depthIdx")
    Integer depthIdx

    @Column(name = "contentsMarkup", columnDefinition = "Text", nullable = false)
    String contentsMarkup

    @Column(name = "contents", columnDefinition = "Text", nullable = false)
    String contents

    @Column(name = "isLock", columnDefinition = "TINYINT(1) DEFAULT 0")
    boolean isLock = false;

    @Column(name = "passwd" , length = 10)
    String passwd

    @Column(name = "userNick", length = 50)
    String userNick

    @Column(name = "userId", nullable = false)
    Integer userId

    @Column(name = "viewCount")
    Integer viewCount

    @Column(name = "likeCount")
    Integer likeCount

    @Column(name = "reportCount")
    Integer reportCount

    @Column(name = "isFixed", columnDefinition = "TINYINT(1) DEFAULT 0")
    boolean isFixed

    @Column(name = "wikiUrl", length = 100)
    String wikiUrl

    @Column(name = "currentIp", length = 16)
    String currentIp

    @Column(name = "editReason", length = 10)
    String editReason

    @Column(name = "revision", length = 10)
    String revision

    @Column(name = "isDeleted", columnDefinition = "TINYINT(1) DEFAULT 0")
    boolean isDeleted

    @Temporal(TemporalType.TIMESTAMP)
    Date insertDate

    @Temporal(TemporalType.TIMESTAMP)
    Date updateDate
}
