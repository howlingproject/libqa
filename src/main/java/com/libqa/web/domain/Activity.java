package com.libqa.web.domain;

import com.libqa.application.enums.ActivityTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Date;

/**
 * @Author : yion
 * @Date : 2015. 6. 21.
 */

@Data
@Entity
@EqualsAndHashCode(of = "activityId")
public class Activity {
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer activityId;

    @Column(nullable = false)
    private Integer userId;

    @Column(length = 40)
    private String userNick;

    @Column(length = 200)
    private String activityDesc;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private ActivityTypeEnum activityType;

    @Column(nullable = true)
    private Integer spaceId;

    @Column(nullable = true)
    private Integer wikiId;

    @Column(columnDefinition = "TINYINT(1) DEFAULT 0", nullable = false)
    private boolean isDeleted;

    @Temporal(TemporalType.DATE)
    private Date insertDate;

}
