package com.plj.hub.user.application.dto.responsedto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class UpdateHubResponseDto {
    private String username;
    private UUID hubId;
}
