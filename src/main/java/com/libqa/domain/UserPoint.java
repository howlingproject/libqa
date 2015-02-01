package com.libqa.domain;


import javax.persistence.*;
import java.util.Date;

/**
 * Created by yion on 2015. 2. 1..
 */
public class UserPoint {

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer userPointId;

    @ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "userId", nullable = false)
    private User user;

    @Column(nullable = false)
    private Integer userPoint;


    @Temporal(TemporalType.TIMESTAMP)
    private Date insertDate;

}
