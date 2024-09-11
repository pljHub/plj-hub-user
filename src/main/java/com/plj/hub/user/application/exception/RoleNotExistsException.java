package com.plj.hub.user.application.exception;

import com.plj.hub.user.global.exception.PljHubException;
import com.plj.hub.user.global.exception.UserErrorCode;

public class RoleNotExistsException extends PljHubException {

    public RoleNotExistsException() {
        super(UserErrorCode.RULE_NOT_EXISTS);
    }
}
