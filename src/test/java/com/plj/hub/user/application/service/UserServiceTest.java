package com.plj.hub.user.application.service;

import com.plj.hub.user.application.dto.responsedto.SignInResponseDto;
import com.plj.hub.user.application.dto.responsedto.SignUpResponseDto;
import com.plj.hub.user.application.exception.AccessDeniedException;
import com.plj.hub.user.application.exception.DuplicatedUsernameException;
import com.plj.hub.user.application.exception.PasswordMismatchException;
import com.plj.hub.user.application.exception.UserNotExistsException;
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
class UserServiceTest {

    @Autowired
    private UserService userService;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private UserRepository userRepository;

    @Transactional
    @Test
    @DisplayName("회원 가입 테스트")
    void signUpTest() {

        String username = "sanghoon";
        String password = "asdf123@";
        String confirmPassword = "asdf123@";
        UserRole role = UserRole.ADMIN;
        String slackId = null;
        UUID uuid = UUID.randomUUID();

        SignUpResponseDto signUpResponseDto = userService.signUp(username, password, confirmPassword, role, slackId, uuid);
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
        UUID uuid = UUID.randomUUID();

        userService.signUp(username, password, confirmPassword, role, slackId, uuid);
        org.junit.jupiter.api.Assertions.assertThrows(DuplicatedUsernameException.class, () -> userService.signUp(username, password, confirmPassword, role, slackId, uuid));
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

        org.junit.jupiter.api.Assertions.assertThrows(PasswordMismatchException.class, () -> userService.signUp(username, password, confirmPassword, role, slackId, uuid));
    }

    @Test
    @Transactional
    @DisplayName("로그인 성공 테스트")
    void loginTest() {
        String username = "sanghoon";
        String password = "asdf123@";
        String confirmPassword = "asdf123@";
        UserRole role = UserRole.ADMIN;
        String slackId = null;
        UUID uuid = UUID.randomUUID();

        userService.signUp(username, password, confirmPassword, role, slackId, uuid);

        SignInResponseDto signInResponseDto = userService.signIn(username, password);
        String accessToken = signInResponseDto.getAccessToken();
        org.junit.jupiter.api.Assertions.assertTrue(jwtUtils.validateToken(accessToken));
    }

    @Test
    @Transactional
    @DisplayName("로그인 아이디 인증 에러 테스트")
    void loginUsernameExceptionTest() {
        String username = "sanghoon";
        String password = "asdf123@";
        String confirmPassword = "asdf123@";
        UserRole role = UserRole.ADMIN;
        String slackId = null;
        UUID uuid = UUID.randomUUID();

        userService.signUp(username, password, confirmPassword, role, slackId, uuid);

        org.junit.jupiter.api.Assertions.assertThrows(UserNotExistsException.class, ()-> userService.signIn("sanghoon2", password));
    }

    @Test
    @Transactional
    @DisplayName("로그인 비밀번호 인증 에러 테스트")
    void loginPasswordMismatchTest() {
        String username = "sanghoon";
        String password = "asdf123@";
        String confirmPassword = "asdf123@";
        UserRole role = UserRole.ADMIN;
        String slackId = null;
        UUID uuid = UUID.randomUUID();

        userService.signUp(username, password, confirmPassword, role, slackId, uuid);

        org.junit.jupiter.api.Assertions.assertThrows(PasswordMismatchException.class, ()-> userService.signIn(username, "password"));
    }

    @Test
    @Transactional
    @DisplayName("slackId 수정 테스트")
    void updateSlackIdTest() {
        String username = "sanghoon";
        String password = "asdf123@";
        String confirmPassword = "asdf123@";
        UserRole role = UserRole.HUB_DELIVERY_USER;
        String slackId = null;
        UUID uuid1 = UUID.randomUUID();

        String username2 = "sanghoonAdmin";
        String password2 = "asdf123@";
        String confirmPassword2 = "asdf123@";
        UserRole role2 = UserRole.ADMIN;
        String slackId2 = null;
        UUID uuid2 = UUID.randomUUID();

        // 본인이 본인 수정
        SignUpResponseDto signUpResponseDto1 = userService.signUp(username, password, confirmPassword, role, slackId, uuid1);
        SignInResponseDto signInResponseDto1 = userService.signIn(username, password);

        String accessToken = signInResponseDto1.getAccessToken();

        Long currentUserId = Long.parseLong(jwtUtils.extractUserId(accessToken));
        String currentUserRole = jwtUtils.extractUserRole(accessToken);

        String updateSlackId = "sjhty123@naver.com";

        userService.updateSlackId(signUpResponseDto1.getUserId(), currentUserId, currentUserRole, updateSlackId);

        User updatedUser = userRepository.findById(signUpResponseDto1.getUserId()).get();
        Assertions.assertThat(updatedUser.getSlackId()).isEqualTo(updateSlackId);

        // ADMIN 으로 수정하기 테스트

        userService.signUp(username2, password2, confirmPassword2, role2, slackId2, uuid2);

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
        String username = "sanghoon";
        String password = "asdf123@";
        String confirmPassword = "asdf123@";
        UserRole role = UserRole.HUB_DELIVERY_USER;
        String slackId = null;
        UUID uuid1 = UUID.randomUUID();

        String username2 = "sanghoon2r";
        String password2 = "asdf123@";
        String confirmPassword2 = "asdf123@";
        UserRole role2 = UserRole.HUB_DELIVERY_USER;
        String slackId2 = null;
        UUID uuid2 = UUID.randomUUID();

        // 본인이 본인 수정
        SignUpResponseDto signUpResponseDto1 = userService.signUp(username, password, confirmPassword, role, slackId, uuid1);
        SignInResponseDto signInResponseDto1 = userService.signIn(username, password);

        String accessToken = signInResponseDto1.getAccessToken();

        Long currentUserId = Long.parseLong(jwtUtils.extractUserId(accessToken));
        String currentUserRole = jwtUtils.extractUserRole(accessToken);

        String updateSlackId = "sjhty123@naver.com";

        userService.updateSlackId(signUpResponseDto1.getUserId(), currentUserId, currentUserRole, updateSlackId);

        User updatedUser = userRepository.findById(signUpResponseDto1.getUserId()).get();
        Assertions.assertThat(updatedUser.getSlackId()).isEqualTo(updateSlackId);

        // ADMIN 으로 수정하기 테스트

        userService.signUp(username2, password2, confirmPassword2, role2, slackId2, uuid2);

        SignInResponseDto signInResponseDto2 = userService.signIn(username2, password2);
        String accessToken2 = signInResponseDto2.getAccessToken();

        Long currentUserId2 = Long.parseLong(jwtUtils.extractUserId(accessToken2));
        String currentUserRole2 = jwtUtils.extractUserRole(accessToken2);

        String updateSlackId2 = "sjhty123aaa@naver.com";

        org.junit.jupiter.api.Assertions.assertThrows(AccessDeniedException.class,
                () -> userService.updateSlackId(signUpResponseDto1.getUserId(), currentUserId2, currentUserRole2, updateSlackId2));
    }
}