package com.libqa.web.domain;

import com.libqa.application.enums.SpaceView;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by yion on 2015. 2. 1..
 */
@Data
@Entity
@EqualsAndHashCode
public class Space implements Serializable {
    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer spaceId;

    @Column(nullable = false, columnDefinition = "Text")    // html
    private String description;

    @Column(nullable = false, columnDefinition = "Text")    // markup
    private String descriptionMarkup;

    @Column(length = 80, nullable = false)
    private String title;

    @Column(length = 40)
    private String titleImage;

    @Column(length = 50)
    private String titleImagePath;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private SpaceView layoutType;

    @Column(columnDefinition = "TINYINT(1) DEFAULT 0", nullable = false)
    private boolean isPrivate;

    @Column(columnDefinition = "TINYINT(1) DEFAULT 0", nullable = false)
    private boolean isDeleted;


    @Temporal(TemporalType.TIMESTAMP)
    private Date insertDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updateDate;

    @Column(nullable = false)
    private Integer insertUserId;

    @Column(length = 40)
    private String insertUserNick;

    @Column
    private Integer updateUserId;

    @Column(length = 40)
    private String updateUserNick;

    @OneToMany(mappedBy = "space", fetch = FetchType.LAZY)
    private List<SpaceAccessUser> spaceAccessUsers;

    @Transient
    private String uploadYn;
}

