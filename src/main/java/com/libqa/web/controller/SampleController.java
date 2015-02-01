package com.libqa.web.controller;

import com.libqa.domain.User;
import com.libqa.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by yion on 2015. 1. 25..
 */
@RestController
@RequestMapping("/")
public class SampleController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping("index")
    public String index() {
        System.out.println("#### index comming !!!!");
        return "Greeting from Sping Boot";
    }

    @RequestMapping("users")
    @ResponseBody
    public List<User> getUsers() {
        return userRepository.findAll();
    }
}

