package com.plj.hub.user.application.service.signup;

import com.plj.hub.user.domain.model.CompanyDeliveryUser;
import com.plj.hub.user.domain.model.User;
import com.plj.hub.user.domain.model.UserRole;
import com.plj.hub.user.infrastructure.client.hub.HubClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class CompanyDeliveryUserSignUp implements SignUp{

    private final HubClientService hubClientService;


    @Override
    public User signUp(String username, String password, String slackId, UUID hubId, UUID companyId) {

        hubClientService.verifyExistsHub(hubId);

        return CompanyDeliveryUser.signUp(username, password, slackId, hubId);
    }

    @Override
    public UserRole getPerimitUserRole() {
        return UserRole.COMPANY_DELIVERY_USER;
    }


}
