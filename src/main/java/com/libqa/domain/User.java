package com.libqa.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by yion on 2015. 1. 25..
 */
@Data
@Entity
@EqualsAndHashCode
public class User {

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer userId;

    @Column(length = 80, nullable = false)
    private String userEmail;

    @Column(length = 80, nullable = false)
    private String userNick;

    @Column(length = 100, nullable = false)
    private String userSite;

    @Column(length = 40, nullable = false)
    private String userImage;

    @Column(length = 50)
    private String userImagePath;

    @Column(columnDefinition = "TINYINT(1) DEFAULT 0")
    private boolean isDeleted;  // (Y : 탈퇴, N: 활성)

    @Column(length = 255, nullable = false)
    private String faceBookKey;

    @Column(length = 255, nullable = false)
    private String googleKey;

    @Column(length = 255, nullable = false)
    private String tweeterKey;

    @Column(length = 255, nullable = false)
    private String userPass;


    @Column(nullable = false, columnDefinition = "int default 0")
    private Integer visiteCount;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastVisiteDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date insertDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updateDate;

    @Column(nullable = false, columnDefinition = "int default 0")
    private Integer userPoint;

    @Column(columnDefinition = "TINYINT(1) DEFAULT 0")
    private boolean isCertification;

    @Column(length = 255, nullable = false)
    private String certificationKey;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<UserKeyword> userKeywords;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<UserPoint> userPoints;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<UserFavorite> userFavorites;




}
