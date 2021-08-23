package com.bitchoi.landbnbserver.constant;

import org.springframework.http.HttpStatus;

public enum ErrorCode {

    MEMBER_NOT_FOUND(404, HttpStatus.NOT_FOUND, "존재하지않는 사용자입니다"),

    EMAIL_ALREADY_USED(409, HttpStatus.CONFLICT, "이미 사용중인 이메일입니다"),
    WRONG_EMAIL_OR_PASSWORD(401, HttpStatus.UNAUTHORIZED, "이메일 또는 패스워드가 틀렸습니다."),

    REFRESH_TOKEN_EXPIRED(401, HttpStatus.UNAUTHORIZED, "새로고침 토큰이 만료되었습니다"),
    REFRESH_TOKEN_NOT_FOUND(404, HttpStatus.NOT_FOUND, "새로고침 토큰이 존재하지않습니다");

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
