package com.libqa.web.controller;

import com.libqa.application.Exception.UserNotCreateException;
import com.libqa.application.enums.StatusCode;
import com.libqa.application.framework.ResponseData;
import com.libqa.application.util.LoggedUserManager;
import com.libqa.application.util.RequestUtil;
import com.libqa.web.domain.Keyword;
import com.libqa.web.domain.User;
import com.libqa.web.domain.UserKeyword;
import com.libqa.web.service.user.UserKeywordService;
import com.libqa.web.service.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by yion on 2015. 3. 23..
 */
@Slf4j
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private LoggedUserManager loggedUserManager;

    @Autowired
    private UserKeywordService userKeywordService;


    /*
    @RequestMapping(value = "/user/login", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData<User> login(HttpServletRequest request, User loginUser) {
        ResponseData<User> responseData = null;
        String targetUrl = returnUrl(request);

        log.debug("/login = {}", targetUrl);
        log.debug("# userEmail = {}", loginUser.getUserEmail());
        log.debug("# userPass = {}", new BCryptPasswordEncoder().encode(loginUser.getUserPass()));

        User user = userService.findByEmail(loginUser.getUserEmail());
        if (user == null) {
            throw new UsernameNotFoundException(String.format("사용자 정보가 존재하지 않습니다. email : %s", loginUser.getUserEmail()));
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        boolean isUser = encoder.matches(loginUser.getUserPass(), user.getUserPass());

        log.debug("### isUser = {} ", isUser);
        log.debug("### user = {} ", user);

        if (!isUser) {
            responseData = ResponseData.createFailResult(user);
        } else {
            user.setTargetUrl(targetUrl);
            responseData = ResponseData.createSuccessResult(user);
        }

        log.debug("### responseData = {} ", responseData);
        return responseData;
    }
    */

    /**
     * 회원 로그인 form
     *
     * @return

    @RequestMapping("/loginPage")
    public ModelAndView loginPage(HttpServletRequest request) {
        log.info("# loginPage request.get = {}", request.getAttribute("isLogin"));
        log.info("# loginPage request.get = {}", request.getAttribute("userEmail"));
        log.info("# loginPage request.get = {}", request.getAttribute("userRole"));
        String returnUrl = RequestUtil.refererUrl(request, "/index");

        // 이전 페이지 결정 (로그인 및 가입일 경우 index로 이동)
        returnUrl = RequestUtil.checkReturnUrl(returnUrl);
        log.info("# returnUrl = {}", returnUrl);

        RequestUtil.printRequest(request, "UserController.loginPage");

        ModelAndView mav = new ModelAndView("/user/loginPage");

        // 실제 로그인 페이지가 아니라 권한으로 강제 이동 된 페이지에서는 returnUrl을 세팅해서 내려주고, hbs에서 강제로 url 이동 처리함.
        mav.addObject("returnUrl", returnUrl);

        log.debug("### 로그인 정보 페이지로 이동");
        return mav;
    }
     */

    @RequestMapping("/user/signUp")
    public ModelAndView signUp(HttpServletRequest request) {
        String returnUrl = RequestUtil.refererUrl(request, "/index");

        returnUrl = RequestUtil.checkReturnUrl(returnUrl);
        ModelAndView mav = new ModelAndView("/user/form");

        mav.addObject("returnUrl", returnUrl);
        return mav;
    }


    // @PreAuthorize("hasAuthority('ADMIN')")
    // hasAnyRole('USER', 'ADMIN')
    // isFullyAuthenticated() and hasAnyRole(‘customer’, ‘admin’)
    // hasIpAddress(‘127.0.0.1’)
    // hasRole(‘admin’) and hasIpAddress(‘192.168.1.0/24’)

    /**
     * hasAnyAuthority() or hasAnyRole() ('authority' and 'role' are synonyms in Spring Security lingo!) - checks whether the current user has one of the GrantedAuthority in the list.
     * hasAuthority() or hasRole() - as above, but for just one.
     * isAuthenticated() or isAnonymous() - whether the current user is authenticated or not.
     * isRememberMe() or isFullyAuthenticated() - whether the current user is authenticated by 'remember me' token or not.
     * @param request
     * @return
     */

    @PreAuthorize("hasAuthority('USER')")
    @RequestMapping("/user/profile")
    public ModelAndView userProfile(HttpServletRequest request) {

        // TODO user_keyword 가져와야 함
        String returnUrl = RequestUtil.refererUrl(request, "/index");
        User user = loggedUserManager.getUser();

        if (user == null || user.isGuest()) {
            log.debug("# 로그인 사용자 정보가 존재하지 않습니다.");
            return sendAccessDenied();
        }

        User entity = userService.findByUserId(user.getUserId());
        List<UserKeyword> userKeywordList = userKeywordService.findByUserAndIsDeleted(entity);

        ModelAndView mav = new ModelAndView("/user/profile");
        mav.addObject("returnUrl", returnUrl);
        mav.addObject("userEmail", user.getUserEmail());
        mav.addObject("user", entity);
        mav.addObject("userKeywordList", userKeywordList);
        mav.addObject("auth", "true");
        return mav;
    }

    @PreAuthorize("hasAuthority('USER')")
    @RequestMapping(value = "/user/checkDupNick", method = RequestMethod.POST)
    @ResponseBody
    public String checkUserNick(@RequestParam String userNick) {
        User user = userService.findByNick(userNick);

        if (user == null) {
            return "1";
        }
        return "0";
    }

    @PreAuthorize("hasAuthority('USER')")
    @RequestMapping(value = "/user/updateProfile", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData<User> updateProfile(@ModelAttribute User user, @ModelAttribute Keyword keyword) throws IllegalAccessException {
        log.debug("### user = {}", user);
        log.debug("### keyword = {}", keyword);
        User result = userService.updateUserProfileAndKeyword(user, keyword);
        return ResponseData.createSuccessResult(result);
    }


    @RequestMapping("/user/createUser")
    @ResponseBody
    public ResponseData<User> createUser(@RequestParam String loginUserMail,
                                         @RequestParam String loginUserNick,
                                         @RequestParam String loginUserPass,
                                         @RequestParam String loginType, // Social Login type
                                         @RequestParam String targetUrl) {
        log.debug("# userEmail = {}", loginUserMail);
        log.debug("# userNick = {}", loginUserNick);
        log.debug("# userPass = {}", loginUserPass);
        log.debug("# loginType = {}", loginType);
        ResponseData<User> resultData = null;
        User user = new User();
        try {
            User duplicateEmail = userService.findByEmail(loginUserMail);   // 이메일 중복 체크
            User duplicateNick = userService.findByNick(loginUserNick);      // 닉네임 중복 체크

            log.debug("## duplicateEmail = {}", duplicateEmail);
            log.debug("## duplicateNick = {}", duplicateNick);

            if (duplicateEmail == null && duplicateNick == null) {
                user = userService.createUser(loginUserMail, loginUserNick, loginUserPass, loginType);
                user.setTargetUrl(targetUrl);   // 이전 주소 세팅
                resultData = ResponseData.createSuccessResult(user);
            } else {
                user.setUserEmail(loginUserMail);
                user.setUserNick(loginUserNick);

                resultData = new ResponseData();
                resultData.setComment("가입정보의 이메일 혹은 닉네임이 이미 존재합니다. 다른 정보로 가입하세요.");
                resultData.setData(user);
                resultData.setResultCode(StatusCode.DUPLICATE.getCode());
            }

        } catch (UserNotCreateException e) {
            e.printStackTrace();
            user.setUserEmail(loginUserMail);
            user.setUserNick(loginUserNick);
            resultData = ResponseData.createFailResult(user);
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

    public ModelAndView sendAccessDenied() {
        ModelAndView mav = new ModelAndView("/common/403");
        mav.addObject("msg", "Hi " + ", you do not have permission to access this page!");

        return mav;
    }

}

