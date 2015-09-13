package com.libqa.web.domain;

import com.libqa.application.enums.KeywordType;
import com.libqa.application.enums.ProcessType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Date;

/**
 * @Author : yion
 * @Date : 2015. 4. 26.
 * @Description :
 */
@Data
@Entity
@Table(name = "send_mail_info",
        indexes = {@Index(name = "send_mail_info_index1",  columnList="senderId", unique = false),
                @Index(name = "send_mail_info_index2",  columnList="receiverId", unique = false),
                @Index(name = "send_mail_info_index3", columnList="mailType",     unique = false)})
@EqualsAndHashCode
public class SendMailInfo {

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer sendMailInfoId;

    @Column(nullable = false)
    private Integer senderId;

    @Column(nullable = false)
    private String senderEmail;

    @Column(nullable = false)
    private Integer receiverId;

    @Column(nullable = false)
    private String receiverEmail;

    @Enumerated(EnumType.STRING)
    @Column(length = 10, nullable = true)
    private KeywordType mailType;

    @Enumerated(EnumType.STRING)
    @Column(length = 10, nullable = true)
    private ProcessType sendType;

    @Column(length = 100, nullable = true)
    private String linkUrl;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "Text")
    private String contents;

    @Temporal(TemporalType.DATE)
    private Date insertDate;

    @Temporal(TemporalType.DATE)
    private Date confirmDate;


}
