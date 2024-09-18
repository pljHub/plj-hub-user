package com.plj.hub.user.global.exception;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum UserErrorCode implements ErrorCode{
    USERNAME_DUPLICATED(HttpStatus.CONFLICT, "중복된 사용자가 존재합니다."),
    PASSWORD_MISMATCH(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다."),
    USER_NOT_EXISTS(HttpStatus.BAD_REQUEST, "존재하지 않는 아이디입니다."),
    ACCESS_DENIED(HttpStatus.FORBIDDEN, "권한이 없습니다."),
    SLACK_ID_DUPLICATED(HttpStatus.CONFLICT, "이미 존재하는 슬랙 계정입니다."),
    RULE_NOT_EXISTS(HttpStatus.BAD_REQUEST, "존재하지 않는 권한입니다."),
    USER_ALREADY_ACTIVATED(HttpStatus.BAD_REQUEST, "이미 활성화된 계정입니다."),
    ;

    private final HttpStatus status;
    private final String message;

    @Override
    public HttpStatus getStatus() {
        return status;
    }

    @Override
    public String getCode() {
        return this.name();
    }

    @Override
    public String getMessage() {
        return message;
    }
}
