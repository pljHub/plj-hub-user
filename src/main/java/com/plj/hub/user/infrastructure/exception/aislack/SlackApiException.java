package com.plj.hub.user.infrastructure.exception.aislack;

import com.plj.hub.user.global.exception.AiSlackErrorCode;
import com.plj.hub.user.global.exception.ErrorCode;
import com.plj.hub.user.global.exception.PljHubException;

public class SlackApiException extends PljHubException {

    public SlackApiException() {
        super(AiSlackErrorCode.SLACK_API_EXCEPTION);
    }
}
