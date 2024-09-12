package com.plj.hub.user.infrastructure.dto.hub.responsedto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class GetHubResponseDto {

    private UUID hubId;
    private String name;
    private String address;
    private String latitude;
    private String longitude;
}
