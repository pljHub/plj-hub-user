package com.plj.hub.user.application.exception;

import com.plj.hub.user.global.exception.PljHubException;
import com.plj.hub.user.global.exception.UserErrorCode;

public class DuplicatedSlackIdException extends PljHubException {
    public DuplicatedSlackIdException() {
        super(UserErrorCode.SLACK_ID_DUPLICATED);
    }
}
