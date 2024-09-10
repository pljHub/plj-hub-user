package com.plj.hub.user.application.dto.requestdto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UpdateHubRequestDto {
    @NotNull(message = "hubId를 입력해주세요")
    private UUID hubId;
}
