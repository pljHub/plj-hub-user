package com.plj.hub.user.infrastructure.client.hub;

import com.plj.hub.user.global.dto.ResponseDto;
import com.plj.hub.user.infrastructure.dto.hub.responsedto.GetCompanyResponseDto;
import com.plj.hub.user.infrastructure.dto.hub.responsedto.GetHubResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "HUB-SERVICE")
public interface HubClient {
    /*
     * 허브에 유저 등록 api 필요 hubManager를 등록
     */


    /*
     * 허브에 유저 등록 api 필요 CompanyDeliveryUser를 등록
     */

    @GetMapping("/api/hubs/{hubId}")
    ResponseEntity<ResponseDto<GetHubResponseDto>> getHubById(@PathVariable("hubId") UUID hubId);

    @GetMapping("/api/companies/{companyId}")
    ResponseEntity<ResponseDto<GetCompanyResponseDto>> getCompanyById(@PathVariable("companyId") UUID companyId);

}
