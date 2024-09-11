package com.plj.hub.user.application.dto.responsedto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpResponseDto {
    private Long userId;
    private String username;
    private String role;

    public SignUpResponseDto(Long userId, String username, String role) {
        this.userId = userId;
        this.username = username;
        this.role = role;
    }
}
