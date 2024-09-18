package com.plj.hub.user.application.exception;

import com.plj.hub.user.global.exception.AiSlackErrorCode;
import com.plj.hub.user.global.exception.ErrorCode;
import com.plj.hub.user.global.exception.PljHubException;

public class SecureCodeNotMatchException extends PljHubException {
    public SecureCodeNotMatchException() {
        super(AiSlackErrorCode.SECURE_CODE_NOT_MATCH);
    }
}
