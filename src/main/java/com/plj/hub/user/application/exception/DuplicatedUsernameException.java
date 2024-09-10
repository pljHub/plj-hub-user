package com.plj.hub.user.application.exception;

import com.plj.hub.user.global.exception.PljHubException;
import com.plj.hub.user.global.exception.UserErrorCode;

public class DuplicatedUsernameException extends PljHubException {

    public DuplicatedUsernameException() {
        super(UserErrorCode.USERNAME_DUPLICATED);
    }
}
