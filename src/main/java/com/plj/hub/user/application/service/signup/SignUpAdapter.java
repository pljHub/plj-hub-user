package com.plj.hub.user.application.service.signup;

import com.plj.hub.user.application.exception.RoleNotExistsException;
import com.plj.hub.user.domain.model.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Component
public class SignUpAdapter {

    protected static final Map<UserRole, SignUp> signUpHandlerMap = new EnumMap<>(UserRole.class);

    @Autowired
    public SignUpAdapter(List<SignUp> signUpHandlerList) {
        for (SignUp signUpHandler : signUpHandlerList) {
            signUpHandlerMap.put(signUpHandler.getPerimitUserRole(), signUpHandler);
        }
    }

    public SignUp getSignUpHandler(UserRole userRole) {
        if (!supports(userRole)) {
            throw new RoleNotExistsException();
        }
        return signUpHandlerMap.get(userRole);
    }

    private boolean supports(UserRole userRole) {
        return signUpHandlerMap.containsKey(userRole);
    }

}
