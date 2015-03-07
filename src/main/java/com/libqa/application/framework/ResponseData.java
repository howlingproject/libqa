package com.libqa.application.framework;

import com.libqa.application.enums.StatusCodeEnum;

import lombok.Data;

/**
 * 응답 결과를 반환한다.
 * Created by yion on 2015. 2. 20..
 */
@Data
public class ResponseData<T> {

    private int resultCode;
    private String comment;
    private T data;

    public ResponseData(int code, String comment, T data) {
        init(code, comment, data);
    }

    public ResponseData() {
    }

    private void init(int code, String comment, T data) {
        this.resultCode = code;
        this.comment = comment;
        this.data = data;
    }

    public static <T> ResponseData<T> createResult(int code, String comment, T data) {
        return new ResponseData<>(code, comment, data);
    }

    public static <T> ResponseData<T> createSuccessResult(T data) {
        return new ResponseData<>(StatusCodeEnum.SUCCESS.getCode(), StatusCodeEnum.SUCCESS.getComment(), data);
    }

    public static <T> ResponseData<T> createFailResult(T data) {
        return new ResponseData<>(StatusCodeEnum.FAIL.getCode(), StatusCodeEnum.FAIL.getComment(), data);
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
