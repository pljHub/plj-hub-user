package com.plj.hub.user.application.service.signup;

import com.plj.hub.user.domain.model.CompanyDeliveryUser;
import com.plj.hub.user.domain.model.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CompanyDeliveryUserSignUp implements SignUp{
    @Override
    public User signUp(String username, String password, String slackId, UUID hubId) {
        return CompanyDeliveryUser.signUp(username, password, slackId, hubId);
    }
}
