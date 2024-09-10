package com.plj.hub.user.application.dto.requestdto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignInRequestDto {

    @NotBlank
    private String username;
    @NotBlank
    private String password;
}
