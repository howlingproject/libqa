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
    private Integer feedId;
    
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

    @Column(nullable = false)
    private String userNick;
    
    @Column(nullable = false)
    private Integer insertUserId;

    @Column(length = 40)
    private Integer updateUserId;   
}
