package com.plj.hub.user.application.service.signup;

import com.plj.hub.user.domain.model.CompanyManager;
import com.plj.hub.user.domain.model.User;
import com.plj.hub.user.infrastructure.client.Hub.HubClient;
import com.plj.hub.user.infrastructure.client.Hub.HubClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CompanyManagerSignUp implements SignUp{

    private final HubClientService hubClientService;

    @Override
    public User signUp(String username, String password, String slackId, UUID hubId, UUID companyId) {

        hubClientService.verifyExistsCompany(companyId);

        return CompanyManager.signUp(username, password, slackId, companyId);
    }
}
