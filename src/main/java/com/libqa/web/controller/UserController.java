package com.libqa.web.controller;

import com.libqa.application.Exception.UserNotCreateException;
import com.libqa.application.enums.StatusCodeEnum;
import com.libqa.application.framework.ResponseData;
import com.libqa.web.domain.User;
import com.libqa.web.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by yion on 2015. 3. 23..
 */
@Slf4j
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/user/login", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData<User> login(HttpServletRequest request, User loginUser) {
        ResponseData<User> responseData = null;
        String targetUrl = returnUrl(request);

        log.info("/login = {}", targetUrl);
        log.info("# userEmail = {}", loginUser.getUserEmail());
        log.info("# userPass = {}", new BCryptPasswordEncoder().encode(loginUser.getUserPass()));

        User user = userService.findByEmail(loginUser.getUserEmail());
        if (user == null) {
            throw new UsernameNotFoundException(String.format("사용자 정보가 존재하지 않습니다. email : %s", loginUser.getUserEmail()));
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        boolean isUser = encoder.matches(loginUser.getUserPass(), user.getUserPass());

        log.info("### isUser = {} ", isUser);
        log.info("### user = {} ", user);

        if (!isUser) {
            responseData = ResponseData.createFailResult(user);
        } else {
            user.setTargetUrl(targetUrl);
            responseData = ResponseData.createSuccessResult(user);
        }

        log.info("### responseData = {} ", responseData);
        return responseData;
    }

    /**
     * 회원 가입 form
     *
     * @return
     */
    @RequestMapping("/user/signup")
    public ModelAndView signUp(HttpServletRequest request) {
        String targetUrl = returnUrl(request);

        ModelAndView mav = new ModelAndView("/user/form");
        mav.addObject("targetUrl", targetUrl);
        return mav;
    }



    @RequestMapping("/user/createUser")
    @ResponseBody
    public ResponseData<User> createUser(@RequestParam String userEmail,
                                         @RequestParam String userNick,
                                         @RequestParam String userPass,
                                         @RequestParam String loginType, // Social Login type
                                         @RequestParam String targetUrl) {
        log.info("# userEmail = {}", userEmail);
        log.info("# userNick = {}", userNick);
        log.info("# userPass = {}", userPass);
        log.info("# loginType = {}", loginType);
        ResponseData<User> resultData = null;
        User user = new User();
        try {
            User duplicateEmail = userService.findByEmail(userEmail);   // 이메일 중복 체크
            User duplicateNick = userService.findByNick(userNick);      // 닉네임 중복 체크

            if (duplicateEmail == null && duplicateNick == null) {
                user = userService.createUser(userEmail, userNick, userPass, loginType);
                user.setTargetUrl(targetUrl);   // 이전 주소 세팅
                resultData = resultData.createSuccessResult(user);
            } else {
                user.setUserEmail(userEmail);
                user.setUserNick(userNick);

                resultData = new ResponseData();
                resultData.setComment("가입정보의 이메일 혹은 닉네임이 이미 존재합니다. 다른 정보로 가입하세요.");
                resultData.setData(user);
                resultData.setResultCode(StatusCodeEnum.DUPLICATE.getCode());
            }

        } catch (UserNotCreateException e) {
            e.printStackTrace();
            user.setUserEmail(userEmail);
            user.setUserNick(userNick);
            resultData = resultData.createFailResult(user);
        }

        return resultData;
    }

    /**
     * 사용자 인증 페이지
     * 메일로 전달된 사용자의 인증 키 값을 통해 사용자 인증 처리한다.
     *
     * @param userId
     * @param certificationKey
     * @return
     */
    @RequestMapping("/userAuth/{userId}/{certificationKey}")
    public ModelAndView userAuth(@PathVariable Integer userId, @PathVariable Integer certificationKey) {
        Assert.notNull(userId, "접근 주소가 잘못 되었습니다.");
        Assert.notNull(certificationKey, "유효하지 않은 접근입니다.");

        int result = 0;
        String msg = "";
        try {
            result = userService.updateForCertificationByKey(userId, certificationKey);
            msg = "정상적으로 인증 되었습니다";
        } catch (UserNotCreateException e) {
            log.error("@ 사용자 인증시 에러가 발생하였습니다.", e);
            result = -1;
            msg = "사용자 인증 처리 실패 : " + e;
        }

        ModelAndView mav = new ModelAndView("/user/auth");
        mav.addObject("result", result);
        mav.addObject("msg", msg);
        return mav;
    }

    private String returnUrl(HttpServletRequest request) {
        String targetUrl = request.getHeader("Referer");
        log.info("## Using Referer header: " + targetUrl);

        if (targetUrl == null) {
            targetUrl = "/";
        }
        return targetUrl;
    }

    /**
     * 로그인 하기 전의 요청했던 URL을 알아낸다.
     *
     * @param request
     * @param response
     * @return

    private String getReturnUrl(HttpServletRequest request, HttpServletResponse response) {
        RequestCache requestCache = new HttpSessionRequestCache();
        SavedRequest savedRequest = requestCache.getRequest(request, response);
        if (savedRequest == null) {
            return request.getSession().getServletContext().getContextPath();
        }
        return savedRequest.getRedirectUrl();
    }
     */

}

