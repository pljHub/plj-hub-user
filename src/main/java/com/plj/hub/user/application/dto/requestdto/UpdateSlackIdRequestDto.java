package com.plj.hub.user.application.dto.requestdto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateSlackIdRequestDto {

    @NotNull(message = "slackId를 입력해주세요")
    private String slackId;
}
