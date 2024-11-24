package com.project.api.controller;

import com.project.api.dto.request.LoginRequestDto;
import com.project.api.dto.request.UserRequestDto;
import com.project.api.dto.request.UserSaveDto;
import com.project.api.dto.response.LoginResponseDto;
import com.project.api.exception.LoginFailException;
import com.project.api.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/auth")
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    // 이메일 중복 확인
    @GetMapping("/check-email")
    public ResponseEntity<?> checkEmail(String email) {
        boolean flag = userService.checkEmailDuplication(email);
        return ResponseEntity.ok().body(flag);
    }

    // 인증코드 검증 api
    @GetMapping("/code")
    public ResponseEntity<?> verifyCode(String email, String code) {
        log.info("{}'s verify code is [  {}  ]", email, code);
        boolean isMatch = userService.isMatchCode(email, code);
        return ResponseEntity.ok().body(isMatch);
    }

    // 회원가입 마무리 단계
    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody UserSaveDto dto) {

        try {
            // DB 저장 단계
            userService.confirmSignUp(dto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok().body("saved success ^^");
    }

    // 로그인 로직
    @PostMapping("/sign-in")
    public ResponseEntity<?> signIn(@RequestBody LoginRequestDto dto, HttpServletResponse response) {
        log.info("lgoin request - {}", dto);

        try {
            LoginResponseDto loginResponse = userService.authenticate(dto);
            log.info("LoginResponseDto - {}", loginResponse);

            if (dto.isAutoLogin()) {
                log.info("dto's auto login - {}", dto.isAutoLogin());
                // 자동로그인 요청이면 토큰을 쿠키에 저장
                // 쿠키 생성
                Cookie cookie = new Cookie("authToken", loginResponse.getToken());
                cookie.setHttpOnly(true);
                cookie.setPath("/");
                cookie.setMaxAge(60 * 60 * 24 * 30); // 30일
                cookie.setSecure(false); // 로컬 개발 환경에서는 false, 배포 환경에서는 true로 설정
                cookie.setComment("Authentication Token"); // 선택 사항, 쿠키 목적 설명

                // 쿠키를 응답에 추가
                response.addCookie(cookie);
            }

            return ResponseEntity.ok().body(loginResponse);

        } catch (LoginFailException e) {
            // 로그인을 실패한 상황
            String errorMessage = e.getMessage();
            return ResponseEntity.status(422).body(errorMessage);
        }
    }


    @GetMapping("/{userId}")
    public ResponseEntity<?> getUser(@PathVariable Long userId) {
        log.info("User id - {}", userId);
        UserRequestDto foundUser = userService.findUser(userId);
        log.info("found user in controller- {}", foundUser);
        return ResponseEntity.ok().body(foundUser);
    }
}
