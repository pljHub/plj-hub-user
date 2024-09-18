package com.plj.hub.user.application.service;

import com.plj.hub.user.application.dto.responsedto.*;
import com.plj.hub.user.application.exception.AccessDeniedException;
import com.plj.hub.user.application.exception.PasswordMismatchException;
import com.plj.hub.user.application.exception.UserNotExistsException;
import com.plj.hub.user.application.utils.JwtUtils;
import com.plj.hub.user.domain.model.User;
import com.plj.hub.user.domain.model.UserRole;
import com.plj.hub.user.domain.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @BeforeEach
    void beforeTest() {
        String username = "sanghoon";
        String password = "asdf123@";
        String confirmPassword = "asdf123@";
        UserRole role = UserRole.ADMIN;
        String slackId = "afewf";

        userService.signUp(username, password, confirmPassword, role, slackId, null, null);
        userService.signIn(username, password);
    }

    @Test
    @Transactional
    @DisplayName("로그인 성공 테스트")
    void loginTest() {
        String username = "sanghoon";
        String password = "asdf123@";

        SignInResponseDto signInResponseDto = userService.signIn(username, password);
        String accessToken = signInResponseDto.getAccessToken();
        org.junit.jupiter.api.Assertions.assertTrue(jwtUtils.validateToken(accessToken));
    }

    @Test
    @Transactional
    @DisplayName("로그인 아이디 인증 에러 테스트")
    void loginUsernameExceptionTest() {
        String username = "sanghoon123";
        String password = "asdf123@";

        org.junit.jupiter.api.Assertions.assertThrows(UserNotExistsException.class, () -> userService.signIn(username, password));
    }

    @Test
    @Transactional
    @DisplayName("로그인 비밀번호 인증 에러 테스트")
    void loginPasswordMismatchTest() {
        String username = "sanghoon";
        String password = "asdf123@123";

        org.junit.jupiter.api.Assertions.assertThrows(PasswordMismatchException.class, () -> userService.signIn(username, password));
    }

    @Test
    @Transactional
    @DisplayName("slackId 수정 테스트")
    void updateSlackIdTest() {
        String username = "sanghoon123";
        String password = "asdf123@";
        String confirmPassword = "asdf123@";
        UserRole role = UserRole.HUB_DELIVERY_USER;
        String slackId = "slack1";


        String username2 = "sanghoonAdmin";
        String password2 = "asdf123@";
        String confirmPassword2 = "asdf123@";
        UserRole role2 = UserRole.ADMIN;
        String slackId2 = "slack2";


        // 본인이 본인 수정
        SignUpResponseDto signUpResponseDto1 = userService.signUp(username, password, confirmPassword, role, slackId, null, null);
        SignInResponseDto signInResponseDto1 = userService.signIn(username, password);

        String accessToken = signInResponseDto1.getAccessToken();

        Long currentUserId = Long.parseLong(jwtUtils.extractUserId(accessToken));
        String currentUserRole = jwtUtils.extractUserRole(accessToken);

        String updateSlackId = "sjhty123@naver.com";

        userService.updateSlackId(signUpResponseDto1.getUserId(),  updateSlackId);

        User updatedUser = userRepository.findById(signUpResponseDto1.getUserId()).get();
        Assertions.assertThat(updatedUser.getSlackId()).isEqualTo(updateSlackId);

    }

    @Test
    @Transactional
    @DisplayName("slackId 수정 권한 불가 테스트")
    void updateSlackIdAccessDeniedTest() {
        String username = "sanghoon123";
        String password = "asdf123@";
        String confirmPassword = "asdf123@";
        UserRole role = UserRole.HUB_DELIVERY_USER;
        String slackId = null;

        String username2 = "sanghoon2r";
        String password2 = "asdf123@";
        String confirmPassword2 = "asdf123@";
        UserRole role2 = UserRole.HUB_DELIVERY_USER;
        String slackId2 = null;

        // 본인이 본인 수정
        SignUpResponseDto signUpResponseDto1 = userService.signUp(username, password, confirmPassword, role, slackId, null, null);
        SignInResponseDto signInResponseDto1 = userService.signIn(username, password);

        String accessToken = signInResponseDto1.getAccessToken();

        Long currentUserId = Long.parseLong(jwtUtils.extractUserId(accessToken));
        String currentUserRole = jwtUtils.extractUserRole(accessToken);

        String updateSlackId = "sjhty123@naver.com";

        userService.updateSlackId(signUpResponseDto1.getUserId(), updateSlackId);

        User updatedUser = userRepository.findById(signUpResponseDto1.getUserId()).get();
        Assertions.assertThat(updatedUser.getSlackId()).isEqualTo(updateSlackId);
    }

    @Test
    @Transactional
    @DisplayName("hubId 수정")
    void updateHubIdTest() {
        String username = "sanghoon123";
        String password = "asdf123@";
        String confirmPassword = "asdf123@";
        UserRole role = UserRole.HUB_MANAGER;
        String slackId = "test1";
        UUID hubId = UUID.fromString("04f028b5-ffca-45fa-9026-02b5d568a00d");

        UUID toUpdateHubId = UUID.fromString("78a8bd22-0893-413e-97b8-ac68e6dee5e0");
        SignUpResponseDto signUpResponseDto = userService.signUp(username, password, confirmPassword, role, slackId, hubId, null);

        UpdateHubResponseDto updateHubResponse = userService.updateHub(signUpResponseDto.getUserId(), 1L, "ADMIN", toUpdateHubId);
        Assertions.assertThat(updateHubResponse.getHubId()).isEqualTo(toUpdateHubId);
    }

    @Test
    void getPassword() {
        String encode = bCryptPasswordEncoder.encode("asdf1234!");
        System.out.println("encode = " + encode);
    }

    @Test
    @Transactional
    void sendSecureCode() {

        String username = "sjhty";
        String password = "asdf123@";
        String confirmPassword = "asdf123@";
        UserRole role = UserRole.ADMIN;
        String slackId = "tkdgns5817@gmail.com";

        SignUpResponseDto signUpResponseDto = userService.signUp(username, password, confirmPassword, role, slackId, null, null);

        GetUserResponseDto userInternal = userService.getUserInternal(signUpResponseDto.getUserId());

        Long loginUserId = userInternal.getId();
        String slackId1 = userInternal.getSlackId();

        System.out.println("slackId1 = " + slackId1 +" userId = " + loginUserId);

        SendSlackSecureCodeResponseDto activeAccountResponseDto = userService.sendSlackSecureCode(signUpResponseDto.getUserId());
        String secureCode = activeAccountResponseDto.getSecureCode();
        System.out.println("secureCode = " + secureCode);

    }
}