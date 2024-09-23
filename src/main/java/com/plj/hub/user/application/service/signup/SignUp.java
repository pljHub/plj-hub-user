package com.plj.hub.user.application.service.signup;

import com.plj.hub.user.domain.model.User;
import com.plj.hub.user.domain.model.UserRole;

import java.util.UUID;

public interface SignUp {
    User signUp(String username, String password, String slackId, UUID hubId, UUID companyId);

    UserRole getPerimitUserRole();
}
