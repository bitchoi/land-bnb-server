package com.bitchoi.landbnbserver.constant;

import org.springframework.http.HttpStatus;

public enum ErrorCode {

    EMAIL_ALREADY_USED(409, HttpStatus.CONFLICT, "이미 사용중인 이메일입니다");

    private final int code;

    private final HttpStatus httpStatus;

    private final String message;

    ErrorCode(int code, HttpStatus httpStatus, String message) {
        this.code = code;
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public int getCode() {
        return this.code;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getMessage() {
        return message;
    }
}
