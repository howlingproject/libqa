package com.libqa.web.controller;

import com.libqa.application.framework.ErrorPagePath;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
public class ErrorPageController implements ErrorController {

    @RequestMapping(value = ErrorPagePath.DEFAULT_ERROR, method = GET)
    public String defaultError() {
        return error500();
    }

    @RequestMapping(value = ErrorPagePath.ERROR_401, method = GET)
    public String error401() {
        return "common/401";
    }

    @RequestMapping(value = ErrorPagePath.ERROR_403, method = GET)
    public String error403() {
        return "common/403";
    }

    @RequestMapping(value = ErrorPagePath.ERROR_404, method = GET)
    public String error404() {
        return "common/404";
    }

    @RequestMapping(value = ErrorPagePath.ERROR_500, method = GET)
    public String error500() {
        return "common/500";
    }

    @Override
    public String getErrorPath() {
        return ErrorPagePath.DEFAULT_ERROR;
    }

}
