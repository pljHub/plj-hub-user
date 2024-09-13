package com.plj.hub.user.infrastructure.exception.hub;

import com.plj.hub.user.global.exception.HubClientErrorCode;
import com.plj.hub.user.global.exception.PljHubException;

public class HubNotExistsException extends PljHubException {
    public HubNotExistsException() {
        super(HubClientErrorCode.HUB_NOT_EXISTS);
    }
}
