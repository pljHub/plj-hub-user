package com.plj.hub.user.application.service;

import com.plj.hub.user.application.dto.responsedto.SignUpResponseDto;
import com.plj.hub.user.application.exception.DuplicatedUsernameException;
import com.plj.hub.user.application.exception.PasswordMismatchException;
import com.plj.hub.user.application.utils.JwtUtils;
import com.plj.hub.user.domain.model.User;
import com.plj.hub.user.domain.model.UserRole;
import com.plj.hub.user.domain.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@SpringBootTest
class UserSignUpTest {

    @Autowired
    private UserService userService;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private UserRepository userRepository;

    @Transactional
    @Test
    @DisplayName("ADMIN 회원 가입 테스트")
    void AdminSignUpTest() {

        String username = "sanghoon123";
        String password = "asdf123@";
        String confirmPassword = "asdf123@";
        UserRole role = UserRole.ADMIN;
        String slackId = "test1";

        SignUpResponseDto signUpResponseDto = userService.signUp(username, password, confirmPassword, role, slackId, null, null);
        User user = userRepository.findById(signUpResponseDto.getUserId()).get();
        Assertions.assertThat(user.getId()).isEqualTo(signUpResponseDto.getUserId());
    }

    @Transactional
    @Test
    @DisplayName("HUB_MANAGER 회원 가입 테스트")
    void HubManagerSignUpTest() {

        String username = "sanghoon123";
        String password = "asdf123@";
        String confirmPassword = "asdf123@";
        UserRole role = UserRole.HUB_MANAGER;
        String slackId = "test1";
        UUID hubId = UUID.fromString("04f028b5-ffca-45fa-9026-02b5d568a00d");

        SignUpResponseDto signUpResponseDto = userService.signUp(username, password, confirmPassword, role, slackId, hubId, null);
        User user = userRepository.findById(signUpResponseDto.getUserId()).get();
        Assertions.assertThat(user.getId()).isEqualTo(signUpResponseDto.getUserId());
    }

    @Transactional
    @Test
    @DisplayName("HUB_DELIVERY_USER 회원 가입 테스트")
    void HubDeliveryUserSignUpTest() {

        String username = "sanghoon123";
        String password = "asdf123@";
        String confirmPassword = "asdf123@";
        UserRole role = UserRole.HUB_DELIVERY_USER;
        String slackId = "test1";
        UUID hubId = UUID.fromString("04f028b5-ffca-45fa-9026-02b5d568a00d");

        SignUpResponseDto signUpResponseDto = userService.signUp(username, password, confirmPassword, role, slackId, hubId, null);
        User user = userRepository.findById(signUpResponseDto.getUserId()).get();
        Assertions.assertThat(user.getId()).isEqualTo(signUpResponseDto.getUserId());
    }

    @Transactional
    @Test
    @DisplayName("COMPANY_DELIVERY_USER 회원 가입 테스트")
    void CompanyDeliveryUserSignUpTest() {

        String username = "sanghoon123";
        String password = "asdf123@";
        String confirmPassword = "asdf123@";
        UserRole role = UserRole.COMPANY_DELIVERY_USER;
        String slackId = "test1";
        UUID hubId = UUID.fromString("04f028b5-ffca-45fa-9026-02b5d568a00d");

        SignUpResponseDto signUpResponseDto = userService.signUp(username, password, confirmPassword, role, slackId, hubId, null);
        User user = userRepository.findById(signUpResponseDto.getUserId()).get();
        Assertions.assertThat(user.getId()).isEqualTo(signUpResponseDto.getUserId());
    }

    @Transactional
    @Test
    @DisplayName("COMPANY_MANAGER 회원 가입 테스트")
    void CompanyManagerSignUpTest() {

        String username = "sanghoon123";
        String password = "asdf123@";
        String confirmPassword = "asdf123@";
        UserRole role = UserRole.COMPANY_MANAGER;
        String slackId = "test1";
        UUID hubId = UUID.fromString("04f028b5-ffca-45fa-9026-02b5d568a00d");
        UUID companyId = UUID.fromString("e1bea00f-4337-4d13-ad1f-ebb0405a94cb");

        SignUpResponseDto signUpResponseDto = userService.signUp(username, password, confirmPassword, role, slackId, hubId, companyId);
        User user = userRepository.findById(signUpResponseDto.getUserId()).get();
        Assertions.assertThat(user.getId()).isEqualTo(signUpResponseDto.getUserId());
    }


    @Test
    @Transactional
    @DisplayName("중복 아이디 가입 불가 테스트")
    void usernameDuplicateTest() {
        String username = "sanghoon";
        String password = "asdf123@";
        String confirmPassword = "asdf123@";
        UserRole role = UserRole.ADMIN;
        String slackId = null;

        userService.signUp(username, password, confirmPassword, role, slackId, null, null);

        org.junit.jupiter.api.Assertions.assertThrows(DuplicatedUsernameException.class, () -> userService.signUp(username, password, confirmPassword, role, "asdf123", null, null));
    }

    @Test
    @Transactional
    @DisplayName("비밀번호 불일치 에러 발생 테스트")
    void passwordMismatchTest() {
        String username = "sanghoon";
        String password = "asdf123@";
        String confirmPassword = "asdf123@1";
        UserRole role = UserRole.ADMIN;
        String slackId = null;
        UUID uuid = UUID.randomUUID();

        org.junit.jupiter.api.Assertions.assertThrows(PasswordMismatchException.class, () -> userService.signUp(username, password, confirmPassword, role, slackId, uuid, null));
    }


}
