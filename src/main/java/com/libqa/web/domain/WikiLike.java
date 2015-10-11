package com.libqa.web.domain;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by songanji on 2015. 10. 11..
 */
@Entity
@Data
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"userId","wikiId"}),
        @UniqueConstraint(columnNames = {"userId","replyId"})
})
public class WikiLike {

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer likeId;

    @Column(nullable = false)
    private Integer userId;

    private Integer wikiId;

    private Integer replyId;

}