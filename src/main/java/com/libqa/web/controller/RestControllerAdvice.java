package com.libqa.web.controller;

import com.libqa.application.framework.ResponseData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import static com.libqa.application.framework.ResponseData.createFailResult;

@Slf4j
@ControllerAdvice(annotations = RestController.class)
public class RestControllerAdvice {

    @ResponseBody
    @ExceptionHandler(RuntimeException.class)
    public ResponseData handleException(RuntimeException ex) {
        log.error("An error has occurred.", ex);
        return createFailResult();
    }

    @ExceptionHandler(AccessDeniedException.class)
    public void handleAccessDeniedException(AccessDeniedException ex) {
        Optional<AccessDeniedException> optional = Optional.ofNullable(ex);
        if (optional.isPresent()) {
            throw optional.get();
        }
    }
}
