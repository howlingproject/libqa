package com.libqa.web.domain;

import com.libqa.application.enums.FavoriteTypeEnum;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by yion on 2015. 2. 1..
 */
@Data
@Entity
@Table(name = "user_favorite")
public class UserFavorite {

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer favoriteId;

    @Column(name = "userId", nullable = false)
    private Integer userId;

    @Column(nullable = true)
    private Integer spaceId;

    @Column(nullable = true)
    private Integer wikiId;

    @Column(name = "favoriteType", nullable = false)
    @Enumerated(EnumType.STRING)
    private FavoriteTypeEnum favoriteType;

    @Column(nullable = true)
    private Integer qaId;

    @Column(columnDefinition = "TINYINT(1) DEFAULT 0")
    private boolean isDeleted;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date insertDate;

    @Temporal(TemporalType.DATE)
    private Date updateDate;

}
