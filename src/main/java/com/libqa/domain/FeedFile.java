package com.libqa.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by sjune on 2015-02-01.
 *
 * @author sjune
 */
@Data
@Entity
public class FeedFile {
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer feedFileId;

    @Column(nullable = false)
    private String realName;

    @Column(nullable = false)
    private String savedName;

    @Column(nullable = false)
    private String filePath;

    @Column(nullable = false)
    private Integer fileSize;

    @Column
    private Integer downloadCount;

    @Column(columnDefinition = "TINYINT(1) DEFAULT 0")
    private boolean isDeleted;

    @Temporal(TemporalType.DATE)
    private Date insertDate;

    @Temporal(TemporalType.DATE)
    private Date updateDate;

    @Column(nullable = false)
    private Integer userId;

    @Column(nullable = false, length = 40)
    private String userNick;

    @Column(nullable = false)
    private Integer insertUserId;

    @Column
    private Integer updateUserId;
    
    @ManyToOne
    @JoinColumn(name = "feedId", referencedColumnName = "feedId", nullable = false)
    private Feed feed;
}
