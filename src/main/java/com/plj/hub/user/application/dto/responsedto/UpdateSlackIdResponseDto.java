package com.plj.hub.user.application.dto.responsedto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UpdateSlackIdResponseDto {
    private String username;
    private String slackId;
}
