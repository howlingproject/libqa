package com.libqa.web.controller;

import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.libqa.application.util.LibqaConstant.ErrorPagePath.*;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
public class ErrorPageController implements ErrorController {

    @RequestMapping(value = DEFAULT_ERROR, method = GET)
    public String defaultError() {
        return error500();
    }

    @RequestMapping(value = ERROR_401, method = GET)
    public String error401() {
        return "error/401";
    }

    @RequestMapping(value = ERROR_403, method = GET)
    public String error403() {
        return "error/403";
    }

    @RequestMapping(value = ERROR_404, method = GET)
    public String error404() {
        return "error/404";
    }

    @RequestMapping(value = ERROR_500, method = GET)
    public String error500() {
        return "error/500";
    }

    @Override
    public String getErrorPath() {
        return DEFAULT_ERROR;
    }

}
