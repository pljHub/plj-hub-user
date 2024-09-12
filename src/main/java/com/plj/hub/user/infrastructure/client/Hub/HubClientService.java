package com.plj.hub.user.infrastructure.client.Hub;

import com.plj.hub.user.global.dto.ResponseDto;
import com.plj.hub.user.infrastructure.dto.hub.responsedto.GetCompanyResponseDto;
import com.plj.hub.user.infrastructure.exception.hub.CompanyNotExistsException;
import com.plj.hub.user.infrastructure.exception.hub.HubNotExistsException;
import com.plj.hub.user.infrastructure.dto.hub.responsedto.GetHubResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
@RequiredArgsConstructor
public class HubClientService {

    private final HubClient hubClient;

    public void verifyExistsHub(UUID hubId) {

        if (hubId == null) {
            log.warn("hubId null 에러");
            throw new HubNotExistsException();
        }

        ResponseEntity<ResponseDto<GetHubResponseDto>> hubById = hubClient.getHubById(hubId);

        if (hubById.getBody() == null || hubById.getBody().getStatus().equals("error")) {
            log.warn("존재하지 않는 Hub 요청 hubId: {}", hubId);
            throw new HubNotExistsException();
        }
    }

    public void verifyExistsCompany(UUID companyId) {

        if (companyId == null) {
            log.warn("hubId null 에러");
            throw new CompanyNotExistsException();
        }

        ResponseEntity<ResponseDto<GetCompanyResponseDto>> companyById = hubClient.getCompanyById(companyId);

        if (companyById.getBody() == null || companyById.getBody().getStatus().equals("error")) {
            log.warn("존재하지 않는 company 요청 companyId: {}", companyId);
            throw new CompanyNotExistsException();
        }
    }
}
