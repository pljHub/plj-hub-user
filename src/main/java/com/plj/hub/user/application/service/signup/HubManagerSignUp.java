package com.plj.hub.user.application.service.signup;

import com.plj.hub.user.domain.model.HubManager;
import com.plj.hub.user.domain.model.User;
import com.plj.hub.user.domain.model.UserRole;
import com.plj.hub.user.infrastructure.client.hub.HubClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class HubManagerSignUp implements SignUp{

    private final HubClientService hubClientService;

    @Override
    public User signUp(String username, String password, String slackId, UUID hubId, UUID companyId) {

        hubClientService.verifyExistsHub(hubId);

        return HubManager.signUp(username, password, slackId, hubId);
    }

    @Override
    public UserRole getPerimitUserRole() {
        return UserRole.HUB_MANAGER;
    }
}
