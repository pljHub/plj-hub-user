package com.plj.hub.user.global.exception;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum AiSlackErrorCode implements ErrorCode{

    SLACK_API_EXCEPTION(HttpStatus.BAD_REQUEST, "slack 메시지를 보낼 수 없습니다."),
    SECURE_CODE_EXPIRED(HttpStatus.BAD_REQUEST, "인증 시간이 만료되었습니다."),
    SECURE_CODE_NOT_MATCH(HttpStatus.BAD_REQUEST, "인증 코드가 일치하지 않습니다.")
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
