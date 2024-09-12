package com.plj.hub.user.application.dto.requestdto;

import com.plj.hub.user.domain.model.UserRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class SignUpRequestDto {

    @NotBlank(message = "아이디를 입력해 주세요.")
    @Pattern(regexp = "^[a-z0-9]{4,45}$", message = "아이디는 4자 이상 45자 이하, 알파벳 소문자와 숫자를 포함해 주세요.")
    private String username;

    @NotBlank(message = "비밀번호를 입력해 주세요.")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*\\d)(?=.*[~!@#$%^&*])[A-Za-z\\d~!@#$%^&*]{8,15}$",
            message = "비밀번호는 8자 이상 15자 이하, 소문자, 숫자, 특수문자를 포함해 주세요.")
    private String password;

    @NotBlank(message = "비밀번호 확인을 입력해주세요.")
    private String confirmPassword;

    @NotBlank(message = "슬랙 아이디를 입력해주세요.")
    private String slackId;

    @NotNull(message = "권한을 입력해주세요")
    private UserRole role;

    private UUID hubId;

    private UUID companyId;
}
