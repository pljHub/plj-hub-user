package com.plj.hub.user.application.exception;

import com.plj.hub.user.global.exception.AiSlackErrorCode;
import com.plj.hub.user.global.exception.ErrorCode;
import com.plj.hub.user.global.exception.PljHubException;

public class SecureCodeExpiredException extends PljHubException {
    public SecureCodeExpiredException() {
        super(AiSlackErrorCode.SECURE_CODE_EXPIRED);
    }
}
