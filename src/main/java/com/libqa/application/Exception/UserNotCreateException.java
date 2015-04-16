package com.libqa.application.Exception;

import lombok.extern.slf4j.Slf4j;

/**
 * @Author : yion
 * @Date : 2015. 4. 12.
 * @Description :
 */
@Slf4j
public class UserNotCreateException extends Throwable {
    public UserNotCreateException(String msg, Exception e) {
        log.error("### UserNotCreateException Message = {}", msg);
        log.error("### Error Message = {}", e);
    }

    public UserNotCreateException(String msg) {
        super(msg);
    }
}
