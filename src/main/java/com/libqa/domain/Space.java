package com.libqa.domain;

import com.libqa.application.enums.SpaceLayoutType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by yion on 2015. 2. 1..
 */
@Data
@Entity
@Table(name = "space")
@EqualsAndHashCode
public class Space {
    @Id
    @Column(name = "spaceId", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer spaceId;

    @Column(name = "description", nullable = false, columnDefinition="Text")
    private String description;

    @Column(name = "descriptionMarkup", nullable = false, columnDefinition="Text")
    private String descriptionMarkup;

    @Column(name = "title", length = 80, nullable = false)
    private String title;

    @Column(name = "titleImage", length = 40)
    private String titleImage;

    @Column(name = "titleImagePath", length = 50)
    private String titleImagePath;

    @Column(name = "isPrivate", columnDefinition="TINYINT(1) DEFAULT 0")
    private boolean isPrivate;

    @Column(name = "isDeleted", columnDefinition="TINYINT(1) DEFAULT 0")
    private boolean isDeleted;

    @Enumerated(EnumType.STRING)
    @Column(name = "layoutType", length = 20)
    private SpaceLayoutType layoutType;

    @Temporal(TemporalType.TIMESTAMP)
    private Date insertDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updateDate;

    @Column(name = "insertUserId", nullable = false)
    private Integer insertUserId;

    @Column(name = "updateUserId")
    private Integer updateUserId;
}
