package com.plj.hub.user.application.exception;

import com.plj.hub.user.global.exception.PljHubException;
import com.plj.hub.user.global.exception.UserErrorCode;

public class UserNotExistsException extends PljHubException {

    public UserNotExistsException() {
        super(UserErrorCode.USER_NOT_EXISTS);
    }
}
