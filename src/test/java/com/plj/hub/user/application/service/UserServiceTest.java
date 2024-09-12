package com.plj.hub.user.application.service;

import com.plj.hub.user.application.dto.responsedto.SignInResponseDto;
import com.plj.hub.user.application.dto.responsedto.SignUpResponseDto;
import com.plj.hub.user.application.dto.responsedto.UpdateHubResponseDto;
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

    @BeforeEach
    void beforeTest() {
        String username = "sanghoon";
        String password = "asdf123@";
        String confirmPassword = "asdf123@";
        UserRole role = UserRole.ADMIN;
        String slackId = "admin123";

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

        userService.updateSlackId(signUpResponseDto1.getUserId(), currentUserId, currentUserRole, updateSlackId);

        User updatedUser = userRepository.findById(signUpResponseDto1.getUserId()).get();
        Assertions.assertThat(updatedUser.getSlackId()).isEqualTo(updateSlackId);

        // ADMIN 으로 수정하기 테스트

        userService.signUp(username2, password2, confirmPassword2, role2, slackId2, null, null);

        SignInResponseDto signInResponseDto2 = userService.signIn(username2, password2);
        String accessToken2 = signInResponseDto2.getAccessToken();

        Long currentUserId2 = Long.parseLong(jwtUtils.extractUserId(accessToken2));
        String currentUserRole2 = jwtUtils.extractUserRole(accessToken2);

        String updateSlackId2 = "sjhty123aaa@naver.com";

        userService.updateSlackId(signUpResponseDto1.getUserId(), currentUserId2, currentUserRole2, updateSlackId2);

        User updatedUser1 = userRepository.findById(signUpResponseDto1.getUserId()).get();
        Assertions.assertThat(updatedUser1.getSlackId()).isEqualTo(updateSlackId2);
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

        userService.updateSlackId(signUpResponseDto1.getUserId(), currentUserId, currentUserRole, updateSlackId);

        User updatedUser = userRepository.findById(signUpResponseDto1.getUserId()).get();
        Assertions.assertThat(updatedUser.getSlackId()).isEqualTo(updateSlackId);

        // ADMIN 으로 수정하기 테스트

        userService.signUp(username2, password2, confirmPassword2, role2, slackId2, null, null);

        SignInResponseDto signInResponseDto2 = userService.signIn(username2, password2);
        String accessToken2 = signInResponseDto2.getAccessToken();

        Long currentUserId2 = Long.parseLong(jwtUtils.extractUserId(accessToken2));
        String currentUserRole2 = jwtUtils.extractUserRole(accessToken2);

        String updateSlackId2 = "sjhty123aaa@naver.com";
        Long userId = signUpResponseDto1.getUserId();

        org.junit.jupiter.api.Assertions.assertThrows(AccessDeniedException.class,
                () -> userService.updateSlackId(userId, currentUserId2, currentUserRole2, updateSlackId2));
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
}