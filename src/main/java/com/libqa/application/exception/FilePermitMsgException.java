package com.libqa.application.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by yion on 2015. 3. 1..
 */

@EqualsAndHashCode(callSuper = false)
@Data
public class FilePermitMsgException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private String customMsg;

    public FilePermitMsgException(String customMsg) {
        this.customMsg = customMsg;
    }

}
