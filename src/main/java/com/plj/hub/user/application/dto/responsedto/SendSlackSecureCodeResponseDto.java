package com.plj.hub.user.application.dto.responsedto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SendSlackSecureCodeResponseDto {

    private Long userId;
    private String secureCode;
}
