package com.libqa.web.domain;



import com.libqa.application.enums.UserPointEnum;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by yion on 2015. 2. 1..
 */
@Data
@Entity
public class UserPoint {

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer userPointId;

    @ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "userId", nullable = false)
    private User user;

    @Column(nullable = false, columnDefinition = "int default 0")
    private Integer userPoint;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private UserPointEnum pointType;


    @Temporal(TemporalType.DATE)
    private Date insertDate;

}
