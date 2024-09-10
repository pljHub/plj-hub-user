package com.plj.hub.user.application.service.signup;

import com.plj.hub.user.domain.model.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class SignUpAdapter {

    public Map<UserRole, Object> signUpHandlerMap = new HashMap<>();

    @Autowired
    public SignUpAdapter(List<SignUp> signUpHandlerList) {
        for (SignUp signUpHandler : signUpHandlerList) {
            if (signUpHandler instanceof AdminSignUp) {
                signUpHandlerMap.put(UserRole.ADMIN, new AdminSignUp());
            }

            if (signUpHandler instanceof CompanyDeliveryUserSignUp) {
                signUpHandlerMap.put(UserRole.COMPANY_DELIVERY_USER, new CompanyDeliveryUserSignUp());
            }

            if (signUpHandler instanceof HubManagerSignUp) {
                signUpHandlerMap.put(UserRole.HUB_MANAGER, new HubManagerSignUp());
            }

            if (signUpHandler instanceof HubDeliveryUserSignUp) {
                signUpHandlerMap.put(UserRole.HUB_DELIVERY_USER, new HubDeliveryUserSignUp());
            }
        }
    }

    public SignUp getSignUpHandler(UserRole userRole) {
        if (!supports(userRole)) {
            throw new IllegalArgumentException("해당 역할로는 회원가입 할 수 없습니다.");
        }
        return (SignUp) signUpHandlerMap.get(userRole);
    }

    private boolean supports(UserRole userRole) {
        return signUpHandlerMap.containsKey(userRole);
    }

}
