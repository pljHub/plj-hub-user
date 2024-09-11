package com.plj.hub.user.infrastructure.client;

import com.plj.hub.user.global.dto.ResponseDto;
import com.plj.hub.user.infrastructure.HubNotExistsException;
import com.plj.hub.user.infrastructure.dto.responsedto.GetHubResponseDto;
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
}
