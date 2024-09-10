package com.plj.hub.user.application.exception;

import com.plj.hub.user.global.exception.PljHubException;
import com.plj.hub.user.global.exception.UserErrorCode;

public class AccessDeniedException extends PljHubException {
    public AccessDeniedException() {
        super(UserErrorCode.ACCESS_DENIED);
    }
}
