package com.plj.hub.user.application.service.signup;

import com.plj.hub.user.domain.model.HubManager;
import com.plj.hub.user.domain.model.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class HubManagerSignUp implements SignUp{
    @Override
    public User signUp(String username, String password, String slackId, UUID hubId) {
        return HubManager.signUp(username, password, slackId, hubId);
    }
}
