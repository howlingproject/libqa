package com.libqa.web.service;

import com.libqa.application.Exception.UserNotCreateException;
import com.libqa.application.enums.SocialChannelTypeEnum;
import com.libqa.web.domain.User;
import com.libqa.web.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
        loginType = SocialChannelTypeEnum.WEB.name();
        User createUser = null;
        try {
            User user = User.createUser(userEmail, userNick, new BCryptPasswordEncoder().encode(password), loginType);
            createUser = userRepository.save(user);
            log.info("### createUser = {}", createUser);

            // 인증 메일 보내기
            if (loginType.equals(SocialChannelTypeEnum.WEB.name())) {
                sendAuthMail(createUser);
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
        user.setVisiteCount(user.getVisiteCount() + 1);
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
    public User findByEmail(String userEmail) {
        return userRepository.findByUserEmail(userEmail);
    }

    @Override
    public User findByNick(String userNick) {
        return userRepository.findByUserNick(userNick);
    }

    @Override
    public User findByEmailAndIsCertification(String email) {
        User user = this.findByEmail(email);

        if (user.isCertification() == false) {
            return null;
        }

        return user;
    }

    @Override
    public User findByAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        if (StringUtils.isBlank(email)) {
            return null;
        }

        return findByEmail(email);
    }

}
