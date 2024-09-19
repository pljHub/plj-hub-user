package com.plj.hub.user.presentation.controller;

import com.plj.hub.user.application.dto.requestdto.*;
import com.plj.hub.user.application.dto.responsedto.*;
import com.plj.hub.user.application.service.UserService;
import com.plj.hub.user.global.dto.ResponseDto;
import com.plj.hub.user.global.login.CurrentUser;
import com.plj.hub.user.global.login.Login;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /*
     * 회원가입
     */

    @PostMapping("/signup")
    public ResponseEntity<ResponseDto<SignUpResponseDto>> signUp(
            @Valid @RequestBody SignUpRequestDto signUpRequestDto) {
        SignUpResponseDto signUpResponse = userService.signUp(signUpRequestDto.getUsername(), signUpRequestDto.getPassword(), signUpRequestDto.getConfirmPassword(), signUpRequestDto.getRole(), signUpRequestDto.getSlackId(), signUpRequestDto.getHubId(), signUpRequestDto.getCompanyId());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ResponseDto.success(HttpStatus.CREATED.name(), signUpResponse));
    }

    /*
     * 로그인
     */

    @PostMapping("/signin")
    public ResponseEntity<ResponseDto<SignInResponseDto>> signIn(
            @Valid @RequestBody SignInRequestDto signInRequestDto) {
        SignInResponseDto signInResponse = userService.signIn(signInRequestDto.getUsername(), signInRequestDto.getPassword());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseDto.success(HttpStatus.OK.name(), signInResponse));
    }

    /*
     * 슬랙 정보 수정
     */

    @PatchMapping("/slack")
    public ResponseEntity<ResponseDto<UpdateSlackIdResponseDto>> updateSlackId(
            @Login CurrentUser currentUser,
            @Valid @RequestBody UpdateSlackIdRequestDto updateSlackIdRequestDto) {
        UpdateSlackIdResponseDto updateSlackIdResponse = userService.updateSlackId(currentUser.getCurrentUserId(), updateSlackIdRequestDto.getSlackId());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseDto.success(HttpStatus.OK.name(), updateSlackIdResponse));
    }

    /*
     * Hub 수정
     */

    @PatchMapping("/{id}/hub")
    public ResponseEntity<ResponseDto<UpdateHubResponseDto>> updateHubId(
            @PathVariable(name = "id") Long userId,
            @Login CurrentUser currentUser,
            @Valid @RequestBody UpdateHubRequestDto updateHubRequestDto) {
        UpdateHubResponseDto updateHubResponse = userService.updateHub(userId, currentUser.getCurrentUserId(), currentUser.getCurrentUserRole(), updateHubRequestDto.getHubId());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseDto.success(HttpStatus.OK.name(), updateHubResponse));
    }

    /*
     * 회원 삭제(소프트)
     */

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto<DeleteUserResponseDto>> deleteUser(@PathVariable(name = "id") Long userId, @Login CurrentUser currentUser) {
        DeleteUserResponseDto deleteUserResponse = userService.deleteUser(userId, currentUser.getCurrentUserId(), currentUser.getCurrentUserRole());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseDto.success(HttpStatus.OK.name(), deleteUserResponse));
    }

    /*
     * 회원 단 건 조회
     */

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<GetUserResponseDto>> getUser(@PathVariable(name = "id") Long userId, @Login CurrentUser currentUser) {
        GetUserResponseDto getUserResponse = userService.getUser(userId, currentUser.getCurrentUserId(), currentUser.getCurrentUserRole());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseDto.success(HttpStatus.OK.name(), getUserResponse));
    }

    /*
     * 회원 단 건 조회 내부 호출
     */

    @GetMapping("/{id}/internal")
    public ResponseEntity<ResponseDto<GetUserResponseDto>> getUserInternal(@PathVariable(name = "id") Long userId) {
        GetUserResponseDto getUserResponse = userService.getUserInternal(userId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseDto.success(HttpStatus.OK.name(), getUserResponse));
    }

    /*
     * 회원 전체 조회
     */
    @GetMapping
    public ResponseEntity<ResponseDto<Page<GetUserResponseDto>>> getUsers(@Login CurrentUser currentUser, Pageable pageable) {
        Page<GetUserResponseDto> getUsersResponse = userService.getUsers(currentUser.getCurrentUserId(), currentUser.getCurrentUserRole(), pageable);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseDto.success(HttpStatus.OK.name(), getUsersResponse));
    }

    /*
     * 슬랙 인증 코드 요청
     */
    @PostMapping("/secure-code")
    public ResponseEntity<ResponseDto<SendSlackSecureCodeResponseDto>> getSecureCode(@Login CurrentUser currentUser) {
        SendSlackSecureCodeResponseDto sendSlackSecureCodeResponse = userService.sendSlackSecureCode(currentUser.getCurrentUserId());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseDto.success(HttpStatus.OK.name(), sendSlackSecureCodeResponse));
    }

    /*
     * 인증 번호 확인 후 활성화
     */
    @PostMapping("/activate")
    public ResponseEntity<ResponseDto<ActivateAccountResponseDto>> activateAccount(@Login CurrentUser currentUser, @RequestBody ActivateAccountRequestDto activateAccountRequestDto) {
        ActivateAccountResponseDto activateAccountResponseDto = userService.activateAccount(currentUser.getCurrentUserId(), activateAccountRequestDto.getSecureCode());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseDto.success(HttpStatus.OK.name(), activateAccountResponseDto));
    }
}
