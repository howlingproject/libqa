package com.libqa.domain;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by yion on 2015. 1. 25..
 */
@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "age", nullable = false)
    private Integer age;

}
