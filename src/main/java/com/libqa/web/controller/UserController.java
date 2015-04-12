package com.libqa.web.controller;

import com.libqa.application.Exception.UserNotCreateException;
import com.libqa.application.framework.ResponseData;
import com.libqa.web.domain.User;
import com.libqa.web.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by yion on 2015. 3. 23..
 */
@Slf4j
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 로그인 form
     * @param model
     * @return
     */
    @RequestMapping("/user/login")
    public ModelAndView login(Model model) {
        log.info("/login");
        ModelAndView mav = new ModelAndView("/user/login");
        return mav;
    }

    /**
     * 회원 가입 form
     * @return
     */
    @RequestMapping("/user/signup")
    public ModelAndView signUp(HttpServletRequest request) {
        String targetUrl = request.getHeader("Referer");
        log.info("## Using Referer header: " + targetUrl);

        if (targetUrl == null) {
            targetUrl = "/";
        }

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
        ResponseData<User> resultDate = null;
        User user = null;
        try {
            user = userService.createUser(userEmail, userNick, userPass, loginType);
            user.setTargetUrl(targetUrl);
            resultDate = resultDate.createSuccessResult(user);
        } catch (UserNotCreateException e) {
            e.printStackTrace();
            resultDate = resultDate.createFailResult(user);
        }

        return resultDate;
    }

}
