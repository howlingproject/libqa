package com.libqa.domain;

import com.libqa.application.enums.SpaceViewEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by yion on 2015. 2. 1..
 */
@Data
@Entity
@EqualsAndHashCode
public class Space {
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer spaceId;

    @Column(nullable = false, columnDefinition = "Text")
    private String description;

    @Column(nullable = false, columnDefinition = "Text")
    private String descriptionMarkup;

    @Column(length = 80, nullable = false)
    private String title;

    @Column(length = 40)
    private String titleImage;

    @Column(length = 50)
    private String titleImagePath;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private SpaceViewEnum layoutType;

    @Column(columnDefinition = "TINYINT(1) DEFAULT 0")
    private boolean isPrivate;

    @Column(columnDefinition = "TINYINT(1) DEFAULT 0")
    private boolean isDeleted;

    @Temporal(TemporalType.TIMESTAMP)
    private Date insertDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updateDate;

    @Column(nullable = false)
    private Integer insertUserId;

    @Column(nullable = true)
    private Integer updateUserId;

    @OneToMany(mappedBy = "space", fetch = FetchType.LAZY)
    private List<SpaceAccessUser> spaceAccessUsers;
}

