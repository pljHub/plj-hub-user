package com.plj.hub.user.application.exception;

import com.plj.hub.user.global.exception.PljHubException;
import com.plj.hub.user.global.exception.UserErrorCode;

public class UserAlreadyActivated extends PljHubException {
    public UserAlreadyActivated() {
        super(UserErrorCode.USER_ALREADY_ACTIVATED);
    }
}
