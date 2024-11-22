package com.danpoong.onchung.domain.auth.service;

import com.danpoong.onchung.domain.auth.dto.AccessTokenDto;
import com.danpoong.onchung.domain.auth.dto.LoginResponseDto;
import com.danpoong.onchung.domain.auth.dto.SignUpRequestDto;
import com.danpoong.onchung.domain.user.domain.UserInfo;
import com.danpoong.onchung.domain.user.repository.UserRepository;
import com.danpoong.onchung.global.security.jwt.TokenProvider;
import com.danpoong.onchung.global.security.jwt.TokenUtil;
import com.danpoong.onchung.global.security.jwt.domain.Token;
import com.danpoong.onchung.global.security.jwt.dto.TokenDto;
import com.danpoong.onchung.global.security.jwt.repository.TokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final TokenProvider tokenProvider;
    private final TokenRepository tokenRepository;
    private final UserRepository userRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    @Transactional
    public LoginResponseDto login(Long userId, HttpServletRequest req, HttpServletResponse resp) {
        UserInfo userInfo = userRepository.findById(userId)
                .orElseGet(() -> userRepository.findByToken(Token.builder().refreshToken(TokenUtil.getRefreshToken(req)).build())
                        .orElseThrow(() -> new RuntimeException("사용자가 존재하지 않습니다.")));

        TokenDto tokenGenerateDto = generateToken(userInfo.getUserName(), userInfo.getUserLoginId());

        userInfo.getToken().updateToken(tokenGenerateDto.refreshToken());
        tokenRepository.save(userInfo.getToken());
        TokenUtil.updateRefreshTokenCookie(req, resp, tokenGenerateDto.refreshToken());

        return LoginResponseDto.builder()
                .tokenDto(tokenGenerateDto.accessToken())
                .recentPublicOfficeName(userInfo.getRecentPublicOffice().getName())
                .build();
    }

    @Transactional
    public AccessTokenDto signUp(SignUpRequestDto sign, HttpServletResponse response) {
        if (checkPresentLoginId(sign.userLoginId())) { // 존재
            throw new RuntimeException("해당 아이디를 사용하는 사용자가 존재합니다.");
        }

        TokenDto tokenGenerateDto = generateToken(sign.username(), sign.userLoginId());

        UserInfo userInfo = UserInfo.builder()
                .userName(sign.username())
                .userLoginId(sign.userLoginId())
                .token(
                        Token.builder().refreshToken(tokenGenerateDto.refreshToken()).build()
                )
                .build();

        userRepository.save(userInfo);

        TokenUtil.saveRefreshToken(response, tokenGenerateDto.refreshToken());

        return AccessTokenDto.builder().accessToken(tokenGenerateDto.accessToken()).build();
    }

    @Transactional
    public AccessTokenDto reissue(HttpServletRequest req, HttpServletResponse res, TokenDto token) {
        if (!tokenProvider.validateToken(token.refreshToken())) {
            throw new RuntimeException("유효하지 않은 Refresh Token");
        }

        Token reqToken = Token.builder()
                .refreshToken(token.refreshToken())
                .build();

        UserInfo userInfo = userRepository.findByToken(reqToken)
                .orElseThrow(() -> new RuntimeException("사용자가 존재하지 않습니다."));

        if (!userInfo.getToken().getRefreshToken().equals(token.refreshToken())) {
            throw new RuntimeException("저장된 Refresh Token이 일치하지 않습니다.");
        }

        TokenDto tokenGenerateDto = tokenProvider.reissueToken(userInfo.getId().toString());

        TokenUtil.updateRefreshTokenCookie(req, res, token.refreshToken());

        return AccessTokenDto.builder().accessToken(tokenGenerateDto.accessToken()).build();
    }


    private boolean checkPresentLoginId(String loginId) {
        return userRepository.findByUserLoginId(loginId).isPresent();
    }

    private TokenDto generateToken(String username, String userLoginId) {
        Authentication authentication = authenticationManagerBuilder.getObject()
                .authenticate(new UsernamePasswordAuthenticationToken(username, userLoginId));

        return tokenProvider.generateToken(authentication);
    }
}
