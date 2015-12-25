package com.libqa.web.domain;

import com.libqa.application.enums.FavoriteType;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by yion on 2015. 2. 1..
 */
@Data
@Entity
@Table(indexes = {
        @Index(name="IDX_USER_FAVORITE_SPACE_ID",columnList = "spaceId"),
        @Index(name="IDX_USER_FAVORITE_QA_ID",columnList = "qaId"),
        @Index(name="IDX_USER_FAVORITE_WIKI_ID",columnList = "wikiId"),
        @Index(name="IDX_USER_FAVORITE_USER_ID",columnList = "userId")
})
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
    private FavoriteType favoriteType;

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
