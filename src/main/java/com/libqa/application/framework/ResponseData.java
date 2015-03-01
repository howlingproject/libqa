package com.libqa.application.framework;

import com.libqa.application.enums.StatusCodeEnum;

import lombok.Data;

/**
 * 응답 결과를 반환한다.
 * Created by yion on 2015. 2. 20..
 */
@Data
public class ResponseData<T> {

    private StatusCodeEnum StatusCode;
    private String comment;
    private T data;

    public ResponseData(StatusCodeEnum code, String comment, T data) {
        init(code, comment, data);
    }

    private void init(StatusCodeEnum code, String comment, T data) {
        this.StatusCode = code;
        this.comment = comment;
        this.data = data;
    }

    public static <T> ResponseData<T> createResult(StatusCodeEnum code, String comment, T data) {
        return new ResponseData<>(code, comment, data);
    }

    public static <T> ResponseData<T> createSuccessResult(T data) {
        return new ResponseData<>(StatusCodeEnum.SUCCESS, StatusCodeEnum.SUCCESS.getComment(), data);
    }

    public ResponseData<T> setStatusCodeEnum(StatusCodeEnum code) {
        this.StatusCode = code;
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
