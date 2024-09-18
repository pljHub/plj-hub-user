package com.plj.hub.user.application.dto.requestdto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ActivateAccountRequestDto {
    private String secureCode;
}
