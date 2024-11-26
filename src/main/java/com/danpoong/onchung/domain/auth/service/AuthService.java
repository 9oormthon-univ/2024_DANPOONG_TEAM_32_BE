package com.danpoong.onchung.domain.auth.service;

import com.danpoong.onchung.domain.auth.dto.LoginResponseDto;
import com.danpoong.onchung.domain.auth.dto.ReissueResponseDto;
import com.danpoong.onchung.domain.auth.exception.RefreshTokenMismatchException;
import com.danpoong.onchung.domain.user.domain.UserInfo;
import com.danpoong.onchung.domain.user.repository.UserInfoRepository;
import com.danpoong.onchung.global.security.jwt.TokenProvider;
import com.danpoong.onchung.global.security.jwt.TokenUtil;
import com.danpoong.onchung.global.security.jwt.dto.TokenDto;
import com.danpoong.onchung.global.security.oauth.kakao.KakaoApiClient;
import com.danpoong.onchung.global.security.oauth.kakao.KakaoLoginParam;
import com.danpoong.onchung.global.security.oauth.kakao.KakaoUserInfo;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    private final UserInfoRepository userInfoRepository;
    private final TokenProvider tokenProvider;
    private final KakaoApiClient kakaoApiClient;

    @Transactional
    public LoginResponseDto login(HttpServletResponse response, KakaoLoginParam params) {
        String kakaoAccessToken = kakaoApiClient.requestAccessToken(params);
        KakaoUserInfo kakaoUserInfo = kakaoApiClient.requestOAuthInfo(kakaoAccessToken);
        UserInfo userInfo = findOrCreateUser(kakaoUserInfo);

        TokenDto tokenDto = tokenProvider.generateToken(userInfo.getId());
        userInfo.updateRefreshToken(tokenDto.refreshToken());
        TokenUtil.saveRefreshToken(response, tokenDto.refreshToken());

        return LoginResponseDto.builder()
                .isNewUser(userInfo.getBirthDate() == null)
                .accessToken(tokenDto.accessToken())
                .build();
    }

    @Transactional
    public ReissueResponseDto reissueToken(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = TokenUtil.getRefreshToken(request);

        if (!tokenProvider.validateToken(refreshToken)) {
            throw new JwtException("입력 받은 Refresh Token은 잘못되었습니다.");
        }

        UserInfo userInfo = userInfoRepository.findByRefreshToken(refreshToken).orElseThrow(() -> new RefreshTokenMismatchException("일치하는 리프레시 토큰이 존재하지 않습니다."));

        if (!userInfo.getRefreshToken().equals(refreshToken)) {
            throw new RefreshTokenMismatchException("Refresh Token = " + refreshToken);
        }

        TokenDto tokenDto = tokenProvider.reissueToken(userInfo.getId());
        userInfo.updateRefreshToken(tokenDto.refreshToken());
        TokenUtil.updateRefreshTokenCookie(request, response, refreshToken);

        return ReissueResponseDto.builder().accessToken(tokenDto.accessToken()).build();
    }

    private UserInfo findOrCreateUser(KakaoUserInfo kakaoUserInfo) {
        return userInfoRepository.findByEmail(kakaoUserInfo.getEmail())
                .orElseGet(() -> createNewUser(kakaoUserInfo));
    }

    private UserInfo createNewUser(KakaoUserInfo kakaoUserInfo) {
        UserInfo userInfo = UserInfo.builder()
                .email(kakaoUserInfo.getEmail())
                .nickname(kakaoUserInfo.getNickname())
                .build();

        return userInfoRepository.save(userInfo);
    }
}
