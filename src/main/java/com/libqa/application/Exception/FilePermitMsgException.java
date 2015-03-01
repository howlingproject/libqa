package com.libqa.application.Exception;

import lombok.Data;

/**
 * Created by yion on 2015. 3. 1..
 */

@Data
public class FilePermitMsgException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private String customMsg;

    public FilePermitMsgException(String customMsg) {
        this.customMsg = customMsg;
    }

}
