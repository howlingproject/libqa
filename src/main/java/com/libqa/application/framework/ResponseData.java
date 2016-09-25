package com.libqa.application.framework;

import com.libqa.application.enums.StatusCode;
import lombok.Data;

import static com.libqa.application.enums.StatusCode.FAIL;
import static com.libqa.application.enums.StatusCode.SUCCESS;

/**
 * 응답 결과를 반환한다.
 * Created by yion on 2015. 2. 20..
 */
@Data
public class ResponseData<T> {

    private int resultCode;
    private String comment;
    private Object data;

    public ResponseData() {
    }

    private ResponseData(int code, String comment, T data) {
        init(code, comment, data);
    }

    private ResponseData(int code, String comment, Iterable data) {
        this.resultCode = code;
        this.comment = comment;
        this.data = data;
    }
    
    private ResponseData(int code, String comment, Number number) {
        this.resultCode = code;
        this.comment = comment;
        this.data = number;
    }

    private ResponseData(int code, String comment) {
        this.resultCode = code;
        this.comment = comment;
    }

    private void init(int code, String comment, T data) {
        this.resultCode = code;
        this.comment = comment;
        this.data = data;
    }

    public static <T> ResponseData<T> createResult(int code, String comment, T data) {
        return new ResponseData<>(code, comment, data);
    }

    public static <T> ResponseData<T> createResult(StatusCode statusCode) {
        return new ResponseData<>(statusCode.getCode(), statusCode.getComment());
    }

    public static <T> ResponseData<T> createSuccessResult(T data) {
        return new ResponseData<>(SUCCESS.getCode(), SUCCESS.getComment(), data);
    }

    public static <T> ResponseData<T> createSuccessResult(Iterable<T> data) {
        return new ResponseData<>(SUCCESS.getCode(), SUCCESS.getComment(), data);
    }

    public static <T> ResponseData<T> createFailResult(T data) {
        return new ResponseData<>(FAIL.getCode(), FAIL.getComment(), data);
    }

    public static <T> ResponseData<T> createFailResult(Iterable<T> data) {
        return new ResponseData<>(FAIL.getCode(), FAIL.getComment(), data);
    }

    public static <T> ResponseData<T> createFailResult() {
        return new ResponseData<>(FAIL.getCode(), FAIL.getComment());
    }

    public static <T> ResponseData<T> createFailResult(int code, String comment, T data) {
        return new ResponseData<>(code, comment, data);
    }

    public static <T> ResponseData<T> createSuccessResult(Number number) {
        return new ResponseData<>(SUCCESS.getCode(), SUCCESS.getComment(), number);
    }

    public ResponseData<T> setResultCode(int code) {
        this.resultCode = code;
        return this;
    }

    public ResponseData<T> setComment(String comment) {
        this.comment = comment;
        return this;
    }

    public ResponseData<T> setData(T data) {
        this.data = data;
        return this;
    }

}
