package com.plj.hub.user.application.service;

import com.plj.hub.user.application.dto.responsedto.*;
import com.plj.hub.user.application.exception.*;
import com.plj.hub.user.application.service.signup.SignUp;
import com.plj.hub.user.application.service.signup.SignUpAdapter;
import com.plj.hub.user.application.utils.JwtUtils;
import com.plj.hub.user.domain.model.User;
import com.plj.hub.user.domain.model.UserRole;
import com.plj.hub.user.domain.repository.UserRepository;
import com.plj.hub.user.infrastructure.client.HubClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final SignUpAdapter signUpAdapter;
    private final JwtUtils jwtUtils;
    private final HubClientService hubClientService;

    /*
     * 회원 가입
     */

    public SignUpResponseDto signUp(String username, String password, String confirmPassword, UserRole userRole, String slackId, UUID hubId) {

        log.info("회원 가입 요청 username: {}, userRole: {}, slackId: {}", username, userRole, slackId);

        verifySignupException(username, password, confirmPassword, slackId);

        SignUp signUpHandler = signUpAdapter.getSignUpHandler(userRole);

        User user = signUpHandler.signUp(username, bCryptPasswordEncoder.encode(password), slackId, hubId);
        User savedUser = userRepository.save(user);

        log.info("회원 가입 성공 username: {}, userRole: {}, slackId: {}", username, userRole, slackId);

        return new SignUpResponseDto(savedUser.getId(), savedUser.getUsername(), savedUser.getRole());
    }


    // 회원가입 검증
    private void verifySignupException(String username, String password, String confirmPassword, String slackId) {
        verifyDuplicatedSlackId(slackId);
        verifyMismatchPassword(username, password, confirmPassword);
        verifyDuplicatedUsername(username);
    }

    // 중복 아이디 검증
    private void verifyDuplicatedUsername(String username) {
        boolean value = userRepository.existsByUsername(username);
        if (value) {
            log.warn("중복된 아이디로 회원 가입 실패  username: {}", username);
            throw new DuplicatedUsernameException();
        }
    }

    // 중복 slack 아이디 검증
    private void verifyDuplicatedSlackId(String slackId) {
        boolean value = userRepository.existsBySlackId(slackId);
        if (value) {
            log.warn("중복된 slack 아이디로 회원 가입 실패 slackId: {}", slackId);
            throw new DuplicatedSlackIdException();
        }
    }

    // 비밀번호 확인
    private void verifyMismatchPassword(String username, String password, String confirmPassword) {
        if (!password.equals(confirmPassword)) {
            log.warn("비밀번호 확인 실패로 회원 가입 실패  username: {}", username);
            throw new PasswordMismatchException();
        }
    }

    /*
     * 로그인
     */

    public SignInResponseDto signIn(String username, String password) {

        log.info("로그인 요청 username: {}", username);

        User user = findUserByName(username);

        verifyPassword(password, user);

        String accessToken = jwtUtils.generateToken(user);

        log.info("로그인 성공 username: {}", username);

        return new SignInResponseDto(accessToken);

    }

    // 아이디 검증
    private User findUserByName(String username) {
        return userRepository.findByUsernameAndDeletedAtIsNull(username).orElseThrow(UserNotExistsException::new);
    }

    // 비밀번호 검증
    private void verifyPassword(String password, User user) {
        boolean matches = bCryptPasswordEncoder.matches(password, user.getPassword());
        if (!matches) {
            log.warn("로그인 요청, 비밀번호 인증 실패 username: {}", user.getUsername());
            throw new PasswordMismatchException();
        }
    }

    /*
     * 회원 정보 수정 (slackId 변경)
     */
    public UpdateSlackIdResponseDto updateSlackId(Long userId, Long currentUserId, String currentUserRole, String slackId) {

        log.info("slackId 업데이트 요청 userId: {}, currentUserId {}", userId, currentUserId);

        if (!UserRole.valueOf(currentUserRole).equals(UserRole.ADMIN) && !userId.equals(currentUserId)) {
            log.warn("slackId 업데이트 요청 실패 userId: {}, currentUserId {}", userId, currentUserId);
            throw new AccessDeniedException();
        }

        verifyDuplicatedSlackId(slackId);

        User user = findUserById(userId);
        user.changeSlackId(slackId);

        User updatedUser = userRepository.save(user);

        log.info("slackId 업데이트 요청 userId: {}, currentUserIdL {}", userId, currentUserId);

        return new UpdateSlackIdResponseDto(updatedUser.getUsername(), updatedUser.getSlackId());
    }

    private User findUserById(Long userId) {
        return userRepository.findByIdAndDeletedAtIsNull(userId).orElseThrow(UserNotExistsException::new);
    }

    /*
     * 회원 정보 수정 hub
     */
    public UpdateHubResponseDto updateHub(Long userId, Long currentUserId, String currentUserRole, UUID hubId) {
        log.info("hub 업데이트 요청 userId: {}, currentUserId {}", userId, currentUserId);

        User user = findUserById(userId);

        verifyRoleForHub(user);

        hubClientService.verifyExistsHub(hubId);

        if (!UserRole.valueOf(currentUserRole).equals(UserRole.ADMIN) && !userId.equals(currentUserId)) {
            log.warn("hub 업데이트 요청 실패 userId: {}, currentUserId {}", userId, currentUserId);
            throw new AccessDeniedException();
        }

        user.updateHubs(hubId);

        User updatedUser = userRepository.save(user);

        log.info("hub 업데이트 요청 userId: {}, currentUserIdL {}", userId, currentUserId);

        return new UpdateHubResponseDto(updatedUser.getUsername(), hubId);
    }

    private void verifyRoleForHub(User user) {
        if (Arrays.asList(UserRole.ADMIN, UserRole.HUB_DELIVERY_USER).contains(UserRole.valueOf(user.getRole()))) {
            throw new AccessDeniedException();
        }
    }

    /*
     * 유저 삭제
     */
    public DeleteUserResponseDto deleteUser(Long userId, Long currentUserId, String currentUserRole) {

        log.info("유저 삭제 요청 userId: {}, currentUserId: {}, currentUserRole: {}", userId, currentUserId, currentUserRole);

        User user = findUserById(userId);
        User currentUser = findUserById(currentUserId);

        if (!UserRole.valueOf(currentUserRole).equals(UserRole.ADMIN) && !user.equals(currentUser)) {
            log.warn("유저 삭제 요청 실패 userId: {}, currentUserId: {}, currentUserRole: {}", userId, currentUserId, currentUserRole);
            throw new AccessDeniedException();
        }

        user.deleteUser(currentUser);
        User savedUser = userRepository.save(user);

        log.info("유저 삭제 요청 성공 userId: {}, currentUserId: {}, currentUserRole: {}", userId, currentUserId, currentUserRole);

        return new DeleteUserResponseDto(savedUser.getId(), savedUser.getDeletedAt());
    }

    /*
     * 유저 단 건 조회
     */
    public GetUserResponseDto getUser(Long userId, Long currentUserId, String currentUserRole) {

        log.info("유저 단 건 조회 요청 userId: {}, currentUserId: {}, currentUserRole: {}", userId, currentUserId, currentUserRole);

        User user = findUserById(userId);
        User currentUser = findUserById(currentUserId);


        if (isDeliveryUser(currentUserRole) && !userId.equals(currentUserId)) {
            log.warn("배송자 권한 문제로 인한 유저 단 건 조회 실패 userId: {}, currentUserId: {}, currentUserRole: {}", userId, currentUserId, currentUserRole);
            throw new AccessDeniedException();
        }

        if (isHubManager(currentUserRole) && currentUser.getHubId() != user.getHubId()) {
            log.warn("허브 매니저 권한 문제로 인한 유저 단 건 조회 실패 userId: {}, currentUserId: {}, currentUserRole: {}", userId, currentUserId, currentUserRole);
            throw new AccessDeniedException();
        }

        log.info("유저 단 건 조회 요청 성공 userId: {}, currentUserId: {}, currentUserRole: {}", userId, currentUserId, currentUserRole);

        User findUser = findUserById(userId);
        return new GetUserResponseDto(findUser);
    }

    private boolean isDeliveryUser(String currentUserRole) {
        return Arrays.asList(UserRole.HUB_DELIVERY_USER, UserRole.COMPANY_DELIVERY_USER).contains(UserRole.valueOf(currentUserRole));
    }

    private boolean isHubManager(String currentUserRole) {
        return UserRole.valueOf(currentUserRole).equals(UserRole.HUB_MANAGER);
    }

    /*
     * 유저 전체 조회
     */

    public Page<GetUserResponseDto> getUsers(Long currentUserId, String currentUserRole, Pageable pageable) {

        log.info("유저 전체 조회 요청 currentUserId: {}, currentUserRole: {}", currentUserId, currentUserRole);

        User currentUser = findUserById(currentUserId);

        Page<User> users;

        if (isHubManager(currentUserRole)) {
            log.info("유저 전체 조회 요청 성공 currentUserId: {}, currentUserRole: {}", currentUserId, currentUserRole);
            users = userRepository.findAllByHubIdAndDeletedAtIsNull(currentUser.getHubId(), pageable);
        } else if (UserRole.ADMIN.equals(UserRole.valueOf(currentUserRole))) {
            log.info("유저 전체 조회 요청 성공 currentUserId: {}, currentUserRole: {}", currentUserId, currentUserRole);
            users = userRepository.findAll(pageable);
        } else {
            log.warn("유저 전체 조회 요청 실패 currentUserId: {}, currentUserRole: {}", currentUserId, currentUserRole);
            throw new AccessDeniedException();
        }

        return users.map(GetUserResponseDto::new);
    }

}
