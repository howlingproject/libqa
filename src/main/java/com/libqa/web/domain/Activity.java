package com.libqa.web.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Date;

/**
 * @Author : yion
 * @Date : 2015. 6. 21.
 * @Description :
 */

@Data
@Entity
@EqualsAndHashCode(of = "activityId")
public class Activity {
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer activityId;

    @ManyToOne
    @JoinColumn(name="activityTypeId", referencedColumnName="activityTypeId", nullable=false)
    private ActivityType activityType;

    @Column(nullable = false)
    private Integer userId;

    @Column(length = 40)
    private String userNick;

    @Column(length = 200)
    private String activityDesc;

    @Column(length = 100)
    private String activityLink;


    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "insertDate", nullable = true, columnDefinition = "datetime(6)")
    private Date insertDate;


    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "confirmDate", nullable = true, columnDefinition = "datetime(6)")
    private Date confirmDate;


    @Column(columnDefinition = "TINYINT(1) DEFAULT 0", nullable = false)
    private boolean isConfirmed;

}
