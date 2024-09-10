package com.plj.hub.user.application.exception;

import com.plj.hub.user.global.exception.PljHubException;
import com.plj.hub.user.global.exception.UserErrorCode;

public class PasswordMismatchException extends PljHubException {

    public PasswordMismatchException() {
        super(UserErrorCode.PASSWORD_MISMATCH);
    }
}
