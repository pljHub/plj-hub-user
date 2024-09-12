package com.plj.hub.user.global.exception;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum HubClientErrorCode implements ErrorCode{

    HUB_NOT_EXISTS(HttpStatus.BAD_REQUEST, "존재하지 않는 허브입니다."),
    COMPANY_NOT_EXISTS(HttpStatus.BAD_REQUEST, "존재하지 않는 업체입니다."),
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
