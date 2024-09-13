package com.plj.hub.user.infrastructure.exception.hub;

import com.plj.hub.user.global.exception.HubClientErrorCode;
import com.plj.hub.user.global.exception.PljHubException;

public class CompanyNotExistsException extends PljHubException {
    public CompanyNotExistsException() {
        super(HubClientErrorCode.COMPANY_NOT_EXISTS);
    }
}
