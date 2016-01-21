package com.libqa.web.service.user;

import com.libqa.application.Exception.UserNotCreateException;
import com.libqa.application.enums.SocialChannelType;
import com.libqa.application.util.StringUtil;
import com.libqa.web.domain.Keyword;
import com.libqa.web.domain.User;
import com.libqa.web.domain.UserKeyword;
import com.libqa.web.repository.UserKeywordRepository;
import com.libqa.web.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

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
    private UserRepository userRepository;

    @Autowired
    private UserKeywordRepository userKeywordRepository;

    @Override
    @Transactional
    public User createUser(String userEmail, String userNick, String password, String loginType) throws UserNotCreateException {
        // LoginType 에 따라 소셜 연동 여부를 결정 짓는다.
        loginType = SocialChannelType.WEB.name();
        User createUser = null;
        try {
            User user = User.createUser(userEmail, userNick, new BCryptPasswordEncoder().encode(password), loginType);
            createUser = userRepository.save(user);
            log.debug("### createUser = {}", createUser);

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

    public User findByUserId(Integer userId) {
        return userRepository.findOne(userId);
    }

    /**
     * 최종 방문일 수정
     *
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
    @Transactional
    public User updateUserProfileAndKeyword(User user, Keyword keyword) {
        User entity = null;

        try {
            entity = userRepository.findOne(user.getUserId());
            bindProfileUpdateInfo(user, entity);
            entity = userRepository.save(entity);

            if (keyword.getKeywords() != null) {
                saveUserKeyword(entity, keyword);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return entity;
    }

    /**
     * 프로필 업데이트 파라미터 세팅
     *
     * @param user
     * @param entity
     */
    public void bindProfileUpdateInfo(User user, User entity) {
        if (StringUtil.nullToString(user.getCheckDupNickname(), "").equals("Y")) {
            entity.setUserNick(user.getUserNick());
        }

        if (StringUtil.nullToString(user.getCheckImageUpload(), "").equals("Y")) {
            entity.setUserImageName(user.getUserImageName());
            entity.setUserImagePath(user.getUserImagePath());
            entity.setUserThumbnailImageName(user.getUserThumbnailImageName());
            entity.setUserThumbnailImagePath(user.getUserThumbnailImagePath());
        }

        if (StringUtil.nullToString(user.getUserSite(), "").equals("")) {
            entity.setUserSite(user.getUserSite());
        }

        entity.setUpdateDate(new Date());
    }

    private void saveUserKeyword(User user, Keyword keyword) {
        List<UserKeyword> userKeywordList = userKeywordRepository.findByUserAndIsDeleted(user, false);

        Date nowDate = new Date();
        if (!userKeywordList.isEmpty()) {
            for (UserKeyword userKeyword : userKeywordList) {
                userKeyword.setDeleted(true);
                userKeyword.setUpdateDate(nowDate);
                userKeywordRepository.save(userKeyword);
            }
        }

        String[] keywordArrays = keyword.getKeywords().split(",");

        for (int i = 0; i < keywordArrays.length; i++) {
            UserKeyword userKeyword = new UserKeyword();
            userKeyword.setDeleted(false);
            userKeyword.setKeywordName(keywordArrays[i]);
            userKeyword.setUser(user);
            userKeyword.setInsertDate(nowDate);
            userKeywordRepository.save(userKeyword);
        }

    }

}
