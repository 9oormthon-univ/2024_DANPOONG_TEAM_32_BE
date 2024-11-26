package com.danpoong.onchung.domain.auth.controller;

import com.danpoong.onchung.domain.auth.dto.LoginResponseDto;
import com.danpoong.onchung.domain.auth.dto.ReissueResponseDto;
import com.danpoong.onchung.domain.auth.service.AuthService;
import com.danpoong.onchung.global.security.oauth.kakao.KakaoLoginParam;
import com.danpoong.onchung.global.template.ResponseTemplate;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@Slf4j
public class AuthController {
    private final AuthService authService;

    @Operation(summary = "카카오 로그인")
    @PostMapping("/login")
    public ResponseTemplate<LoginResponseDto> kakaoLogin(HttpServletResponse response, @RequestBody KakaoLoginParam kakaoLoginParam) {
        return new ResponseTemplate<>(HttpStatus.OK, "카카오 로그인 성공", authService.login(response, kakaoLoginParam));
    }

    @Operation(summary = "토큰 재발급", description = "만료 시 사용")
    @PostMapping("/reissue")
    public ResponseTemplate<ReissueResponseDto> reissue(HttpServletRequest request, HttpServletResponse response) {
        return new ResponseTemplate<>(HttpStatus.OK, "엑세스 토큰 재발급", authService.reissueToken(request, response));
    }
}
