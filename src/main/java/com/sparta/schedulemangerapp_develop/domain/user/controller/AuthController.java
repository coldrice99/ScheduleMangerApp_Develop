package com.sparta.schedulemangerapp_develop.domain.user.controller;

import com.sparta.schedulemangerapp_develop.domain.user.dto.AuthRequestDto;
import com.sparta.schedulemangerapp_develop.domain.user.service.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    // 회원가입
    @PostMapping("sign-up")
    public ResponseEntity<String> signUp(@RequestBody AuthRequestDto requestDto, HttpServletResponse response) {
        authService.signUp(requestDto, response);
        return ResponseEntity.status(HttpStatus.CREATED).body("회원가입 완료");
    }
}
