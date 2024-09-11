package com.plj.hub.user.infrastructure.client;

import com.plj.hub.user.global.dto.ResponseDto;
import com.plj.hub.user.infrastructure.HubNotExistsException;
import com.plj.hub.user.infrastructure.dto.responsedto.GetHubResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class HubClientService {

    public void verifyExistsHub(ResponseEntity<ResponseDto<GetHubResponseDto>> hubById) {
        if (hubById.getBody() == null || hubById.getBody().getStatus().equals("error")) {
            throw new HubNotExistsException();
        }
    }
}
