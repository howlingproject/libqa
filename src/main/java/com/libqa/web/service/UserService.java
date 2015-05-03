package com.libqa.web.service;

import com.libqa.application.Exception.UserNotCreateException;
import com.libqa.web.domain.User;

/**
 * @Author : yion
 * @Date : 2015. 4. 12.
 * @Description :
 */
public interface UserService {
    /**
     * 회원 가입 (사용자 계정 생성)
     *
     * @param userEmail
     * @param userNick
     * @param password
     * @param loginType
     * @return
     * @throws UserNotCreateException
     */
    User createUser(String userEmail, String userNick, String password, String loginType) throws UserNotCreateException;

    /**
     * 인증 여부 업데이트
     *
     * @param userId
     * @param certificationKey
     * @return
     * @throws UserNotCreateException
     */
    int updateForCertificationByKey(Integer userId, Integer certificationKey) throws UserNotCreateException;

    /**
     * 이메일 중복 확인
     *
     * @param userEmail
     * @return
     */
    User findByEmail(String userEmail);

    /**
     * 닉네임 중복 확인
     *
     * @param userNick
     * @return
     */
    User findByNick(String userNick);

    /**
     * 로그인 처리 (인증 여부 검사)
     *
     * @param email
     * @return
     */
    User findByEmailAndIsCertification(String email);

}
