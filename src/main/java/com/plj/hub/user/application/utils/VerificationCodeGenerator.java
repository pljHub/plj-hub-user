package com.plj.hub.user.application.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class VerificationCodeGenerator {

    private final SecureRandom random = new SecureRandom();
    private final int CODE_LENGTH = 6;

    public String secureCodeGenerator() {
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < CODE_LENGTH; i++) {
            code.append(random.nextInt(10)); // 0부터 9까지의 숫자를 랜덤하게 생성
        }
        return code.toString();
    }


}
