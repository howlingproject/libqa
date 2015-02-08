package com.libqa.web.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by sjune on 2015-01-31.
 *
 * @author sjune
 */
@Controller
public class HelloWorldController {
    
    @Value("${howling.hello.message}")
	private String message;

    @RequestMapping("/")
    public String test(Model model) {
        model.addAttribute("message", message);
        return "test";
    }

    @RequestMapping("/hello")
    public String index(Model model) {
        model.addAttribute("message", message);
        return "hello";
    }

    @RequestMapping("/handlebars")
    public String handlebars(Model model) {
        return "handlebars";
    }
}