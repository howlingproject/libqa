package com.libqa.web.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
    public String helloJsp(Model model) {
        model.addAttribute("message", message);
        return "world";
    }

    @RequestMapping("/hello/{message}")
    @ResponseBody
    public HelloWorld helloJson(@PathVariable String message) {
        return new HelloWorld(message);
    }

    @RequestMapping("/handlebars")
    public String handlebars(Model model) {
        return "handlebars";
    }

    @Data
    @AllArgsConstructor
    public class HelloWorld {
        private String message;
    }
}
