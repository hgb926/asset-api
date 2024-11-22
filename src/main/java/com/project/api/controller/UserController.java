package com.project.api.controller;

import com.project.api.dto.request.UserSaveDto;
import com.project.api.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
