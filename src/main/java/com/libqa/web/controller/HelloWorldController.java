package com.libqa.web.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by sjune on 2015-01-31.
 *
 * @author sjune
 */
@Controller
public class HelloWorldController {

    @Value("${howling.hello.message}")
    private String message;

    @RequestMapping("/hello")
    public ModelAndView helloJsp() {
        ModelAndView mav = new ModelAndView("world");
        mav.addObject("greeting", message);
        return mav;
    }

    @RequestMapping("/hello/{message}")
    @ResponseBody
    public HelloWorld helloJson(@PathVariable String message) {
        return new HelloWorld(message);
    }

    @Data
    @AllArgsConstructor
    public class HelloWorld {
        private String greeting;
    }
}
