package com.plj.hub.user.application.service.signup;

import com.plj.hub.user.domain.model.Admin;
import com.plj.hub.user.domain.model.User;
import com.plj.hub.user.domain.model.UserRole;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AdminSignUp implements SignUp{
    @Override
    public User signUp(String username, String password, String slackId, UUID hubId, UUID companyId) {
        return Admin.signUp(username, password, slackId);
    }

    @Override
    public UserRole getPerimitUserRole() {
        return UserRole.ADMIN;
    }


}
