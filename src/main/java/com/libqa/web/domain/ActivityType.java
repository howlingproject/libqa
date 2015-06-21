package com.libqa.web.domain;

import com.libqa.application.enums.ActivityTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * @Author : yion
 * @Date : 2015. 6. 21.
 * @Description :
 */

@Data
@Entity
@EqualsAndHashCode(of = "activityTypeId")
public class ActivityType {
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer activityTypeId;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private ActivityTypeEnum activityTypeEnum;

    @Column(length = 40)
    private String activityValue;

    @Column
    private Integer activityTableId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "insertDate", nullable = true, columnDefinition = "datetime(6)")
    private Date insertDate;


    @OneToMany(mappedBy = "activityType", fetch = FetchType.LAZY)
    private List<Activity> activities;



}
