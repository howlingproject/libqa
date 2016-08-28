package com.libqa.web.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.libqa.application.enums.Role;
import com.libqa.application.enums.SocialChannelType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import java.io.File;
import java.util.Date;
import java.util.List;

/**
 * Created by yion on 2015. 1. 25..
 */
@Data
@Entity
@EqualsAndHashCode
@ToString(exclude = {"userKeywords", "userPoints"})
@Table(indexes = {
        @Index(name="IDX_USER_ID",columnList = "userId"),
        @Index(name="IDX_USER_EMAIL",columnList = "userEmail"),
        @Index(name="IDX_USER_NICK",columnList = "userNick")
})
public class User {
    private final static String DEFAULT_PROFILE_IMAGE = "/resource/images/avatar.png";

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer userId;

    @Column(length = 80, nullable = false, unique = true)
    private String userEmail;

    @Column(length = 40, nullable = false, unique = true)
    private String userNick;

    @Column(length = 100, nullable = true)
    private String userSite;

    @Column(length = 40, nullable = true)
    private String userImageName;

    @Column(length = 80, nullable = true)
    private String userImagePath;

    @Column(length = 40, nullable = true)
    private String userThumbnailImageName;

    @Column(length = 80, nullable = true)
    private String userThumbnailImagePath;

    @Column(columnDefinition = "TINYINT(1) DEFAULT 0", nullable = false)
    private boolean isDeleted;  // (Y : 탈퇴, N: 활성)

    @Column(length = 255, nullable = false)
    private String userPass;

    @Column(nullable = false, columnDefinition = "int default 0")
    private int visitCount;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date lastVisiteDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date insertDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updateDate;

    @Column(nullable = false, columnDefinition = "int default 0")
    private int userTotalPoint;

    @Column(columnDefinition = "TINYINT(1) DEFAULT 0", nullable = false)
    private boolean isCertification;

    // 인증 키값 (랜덤함수)
    @Column(length = 255, nullable = false)
    private String certificationKey;

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "channelType", length = 20, nullable = true)
    @Enumerated(EnumType.STRING)
    private SocialChannelType channelType;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<UserKeyword> userKeywords;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<UserPoint> userPoints;

    @Transient
    private String targetUrl;

    // 회원 정보 수정시 닉네임 중복확인 여부
    @Transient
    private String checkDupNickname;

    @Transient
    private String checkImageUpload;

    @Transient
    public void increaseVisit() {
        this.visitCount++;
    }

    public static User createUser(String userEmail, String userNick, String password, String channelType) {
        Date now = new Date();
        User user = new User();
        user.setUserEmail(userEmail);
        user.setUserNick(userNick);
        user.setUserPass(password);
        user.setDeleted(false);
        user.setCertification(true); // TODO 추후 메일 연동을 통해 인증 방식으로 처리한다.
        user.setLastVisiteDate(now);
        user.setInsertDate(now);
        user.setUpdateDate(now);
        user.setRole(Role.USER);
        user.setCertificationKey(String.valueOf(System.nanoTime()).substring(0, 5));
        user.setChannelType(SocialChannelType.valueOf(channelType));
        return user;
    }

    public static User createGuest() {
        User user = new User();
        user.setUserId(-1);
        user.setRole(Role.GUEST);
        return user;
    }

    public boolean isGuest() {
        if (this.role == Role.GUEST || this.role == null) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isAdmin() {
        if (this.role == role.ADMIN) {
            return true;
        } else {
            return false;
        }
    }

    public String getUserImage() {
        if (!isRegisteredProfileImage()) {
            return DEFAULT_PROFILE_IMAGE;
        }

        return this.userImagePath + File.separator + this.userImageName;
    }

    private boolean isRegisteredProfileImage() {
        return StringUtils.isNotBlank(this.userImagePath)
                && StringUtils.isNotBlank(this.userImageName);
    }

    public boolean isMatchUser(Integer targetUserId) {
        return ObjectUtils.equals(this.userId, targetUserId);
    }

    public boolean isNotMatchUser(Integer targetUserId) {
        return !isMatchUser(targetUserId);
    }
}
