package com.plj.hub.user.infrastructure.dto.aislack.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SendMessageRequestDto {
    private String email;
    private String message;
}
