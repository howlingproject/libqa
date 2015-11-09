package com.libqa.web.service;

import com.libqa.application.Exception.UserNotCreateException;
import com.libqa.application.enums.SocialChannelType;
import com.libqa.application.util.StringUtil;
import com.libqa.web.domain.Keyword;
import com.libqa.web.domain.User;
import com.libqa.web.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * 회원 가입, 탈퇴, 로그인, 정보 수정
 *
 * @Author : yion
 * @Date : 2015. 4. 12.
 * @Description :
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    public static final String USER_EMAIL_CACHE = "usersCache";

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MailService mailService;

    @Override
    @Transactional
    public User createUser(String userEmail, String userNick, String password, String loginType) throws UserNotCreateException {
        // LoginType 에 따라 소셜 연동 여부를 결정 짓는다.
        loginType = SocialChannelType.WEB.name();
        User createUser = null;
        try {
            User user = User.createUser(userEmail, userNick, new BCryptPasswordEncoder().encode(password), loginType);
            createUser = userRepository.save(user);
            log.info("### createUser = {}", createUser);

            // 인증 메일 보내기
            if (loginType.equals(SocialChannelType.WEB.name())) {
                //sendAuthMail(createUser);
            }

        } catch (Exception e) {
            log.error("User Not Create Exception!!! ", createUser);
            throw new UserNotCreateException("사용자 정보가 생성되지 않았습니다. 에러를 확인하세요.", e);
        }


        return createUser;
    }

    void sendAuthMail(User createUser) {
        log.info("### Send mail ");
        // mailService

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo("someone@localhost");
        mailMessage.setReplyTo("someone@localhost");
        mailMessage.setFrom("someone@localhost");
        mailMessage.setSubject("Lorem ipsum");
        mailMessage.setText("Lorem ipsum dolor sit amet [...]");
        javaMailSender.send(mailMessage);

    }


    @Override
    public int updateForCertificationByKey(Integer userId, Integer certificationKey) throws UserNotCreateException {
        User user = userRepository.findOne(userId);
        int result = 0;
        if (user == null) {
            log.error("@ 회원 인증키 업데이트 실패 : 회원 정보가 존재하지 않음");
            throw new UserNotCreateException("사용자 정보가 존재하지 않습니다.");
        }
        Date now = new Date();
        user.setCertification(true);
        user.setCertificationKey(certificationKey + "");
        user.setVisitCount(user.getVisitCount() + 1);
        user.setLastVisiteDate(now);
        user.setUpdateDate(now);

        try {
            userRepository.save(user);
            result = 1;
        } catch (Exception e) {
            log.error("@ 회원 인증키 업데이트 실행시 에러가 발생했습니다.", e);
            e.printStackTrace();
            result = -1;
        }

        return result;
    }

    @Override
    @Cacheable(value = USER_EMAIL_CACHE)
    public User findByEmail(String email) {
        return userRepository.findByUserEmail(email);
    }

    @Override
    public User findByNick(String userNick) {
        return userRepository.findByUserNick(userNick);
    }

    @Override
    public User findByEmailAndIsCertification(String email) {
        User user = this.findByEmail(email);

        if (!user.isCertification()) {
            return null;
        }

        return user;
    }

    @Override
    public User findOne(Integer userId) {
        return userRepository.findOne(userId);
    }


    /**
     * 최종 방문일 수정
     * @param email
     */
    @Override
    public void updateUserLastVisitDate(String email) {
        User user = this.findByEmail(email);

        user.setLastVisiteDate(new Date());
        user.increaseVisit();
        userRepository.save(user);
    }

    @Override
    public User updateUserProfile(User user, Keyword keyword) {
        log.info("### updateUserProfile = {}", user);

        User entity = userRepository.findOne(user.getUserId());

        log.info("### entity = {}", entity);


        if (StringUtil.nullToString(user.getCheckDupNickname(), "").equals("Y")) {
            entity.setUserNick(user.getUserNick());
        }

        entity.setUserImageName(user.getUserImageName());
        entity.setUserImagePath(user.getUserImagePath());


        entity.setUserThumbnailImageName(user.getUserThumbnailImageName());
        entity.setUserThumbnailImagePath(user.getUserThumbnailImagePath());

        entity.setUserSite(user.getUserSite());
        entity.setUpdateDate(new Date());
        entity = userRepository.save(entity);

        saveUserKeyword(entity, keyword);

        return entity;
    }

    private void saveUserKeyword(User entity, Keyword keyword) {
        // TODO user_keyword 저장로직
    }


}
