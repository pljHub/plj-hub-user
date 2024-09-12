package com.plj.hub.user.application.service.signup;

import com.plj.hub.user.domain.model.HubDeliveryUser;
import com.plj.hub.user.domain.model.User;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class HubDeliveryUserSignUp implements SignUp{
    @Override
    public User signUp(String username, String password, String slackId, UUID hubId, UUID companyId) {
        return HubDeliveryUser.signUp(username, password, slackId);
    }
}
