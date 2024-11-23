package com.danpoong.onchung.domain.auth.controller;

import com.danpoong.onchung.domain.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

//    @Operation(summary = "로그인", description = "만약 엑세스 토큰이 존재하지 않으면 쿠키에서 리프레시 토큰을 찾아 로그인")
//    @PostMapping("/login")
//    public ResponseTemplate<LoginResponseDto> login(@AuthenticationPrincipal Long userId, HttpServletRequest request, HttpServletResponse response) {
//        return new ResponseTemplate<>(HttpStatus.OK, "로그인 성공", authService.login(userId, request, response));
//    }
//
//    @Operation(summary = "회원가입", description = "아이디 중복 확인 버튼 클릭 시 닉네임, 아이디 같이 보내면 중복 검사 밑 회원가입 진행")
//    @PostMapping("/signup")
//    public ResponseTemplate<AccessTokenDto> signup(@RequestBody SignUpRequestDto sign, HttpServletResponse response) {
//        return new ResponseTemplate<>(HttpStatus.CREATED, "회원가입 성공", authService.signUp(sign, response));
//    }
//
//    @Operation(summary = "토큰 재발급")
//    @PostMapping("/reissue")
//    public ResponseTemplate<AccessTokenDto> reissue(@RequestBody TokenDto tokenRequestDto, HttpServletRequest request, HttpServletResponse response) {
//        return new ResponseTemplate<>(HttpStatus.OK, "토큰 재발급 성공", authService.reissue(request, response, tokenRequestDto));
//    }
}
