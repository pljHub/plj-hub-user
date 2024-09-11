package com.plj.hub.user.application.service.signup;

import com.plj.hub.user.domain.model.HubManager;
import com.plj.hub.user.domain.model.User;
import com.plj.hub.user.global.dto.ResponseDto;
import com.plj.hub.user.infrastructure.client.HubClient;
import com.plj.hub.user.infrastructure.client.HubClientService;
import com.plj.hub.user.infrastructure.dto.responsedto.GetHubResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class HubManagerSignUp implements SignUp{

    private final HubClient hubClient;
    private final HubClientService hubClientService;

    @Override
    public User signUp(String username, String password, String slackId, UUID hubId) {
        ResponseEntity<ResponseDto<GetHubResponseDto>> hubById = hubClient.getHubById(hubId);
        hubClientService.verifyExistsHub(hubById);
        return HubManager.signUp(username, password, slackId, hubId);
    }
}
