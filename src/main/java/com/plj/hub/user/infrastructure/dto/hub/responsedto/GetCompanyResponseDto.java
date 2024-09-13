package com.plj.hub.user.infrastructure.dto.hub.responsedto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class GetCompanyResponseDto {
    private UUID companyId;
    private String name;
    private String type;
    private UUID hubId;
    private String address;
}
