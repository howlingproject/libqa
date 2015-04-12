package com.libqa.web.service;

import com.libqa.application.Exception.UserNotCreateException;
import com.libqa.application.enums.RoleEnum;
import com.libqa.application.enums.SocialChannelTypeEnum;
import com.libqa.web.domain.User;
import com.libqa.web.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 회원 가입, 탈퇴, 로그인, 정보 수정
 * @Author : yion
 * @Date : 2015. 4. 12.
 * @Description :
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MailService mailService;

    @Override
    @Transactional
    public User createUser(String userEmail, String userNick, String password, String LoginType) throws UserNotCreateException {
        // LoginType 에 따라 소셜 연동 여부를 결정 짓는다.
        LoginType = SocialChannelTypeEnum.WEB.name();
        User createUser = null;
        try {
            User user = User.createUser(userEmail, userNick, new BCryptPasswordEncoder().encode(password), LoginType);
            createUser = userRepository.save(user);
            log.info("### createUser = {}", createUser);

            sendAuthMail(createUser);
        } catch (Exception e) {
            log.error("User Not Create Exception!!! ", createUser);
            throw new UserNotCreateException("사용자 정보가 생성되지 않았습니다. 에러를 확인하세요.", e);
        }


        return createUser;
    }

    private void sendAuthMail(User createUser) {
        log.info("### Send mail ");
        // mailService
    }
}
