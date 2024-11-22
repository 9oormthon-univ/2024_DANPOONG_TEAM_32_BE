package com.danpoong.onchung.global.security.jwt;

import com.danpoong.onchung.global.security.jwt.dto.TokenDto;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Collections;
import java.util.Date;

@Component
@Slf4j
public class TokenProvider {
    @Value("${jwt.expiration.access}")
    private String accessTokenExpireTime;
    @Value("${jwt.expiration.refresh}")
    private String refreshTokenExpireTime;

    private final Key key;

    public TokenProvider(@Value("${jwt.secret}") String secretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public TokenDto generateToken(Authentication authentication) {
        String accessToken = generateAccessToken(authentication.getName());
        String refreshToken = generateRefreshToken();

        return TokenDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    //Access Token 생성
    public String generateAccessToken(String userId) {
        Date now = new Date();
        Date accessExpiryDate = new Date(now.getTime() + Long.parseLong(accessTokenExpireTime));

        return Jwts.builder()
                .setSubject(String.valueOf(userId))
                .setExpiration(accessExpiryDate)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    //Refresh Token 생성
    public String generateRefreshToken() {
        Date now = new Date();
        Date refreshExpiryDate = new Date(now.getTime() + Long.parseLong(refreshTokenExpireTime));

        return Jwts.builder()
                .setExpiration(refreshExpiryDate)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    public TokenDto reissueToken(String userId) {
        String newAccessToken = generateAccessToken(userId);
        String newRefreshToken = generateRefreshToken();

        return TokenDto.builder()
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken)
                .build();
    }

    public Authentication getAuthentication(String accessToken) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(accessToken)
                .getBody();

        UserDetails principal = new User(claims.getSubject(), "", Collections.emptyList());

        return new UsernamePasswordAuthenticationToken(principal, "", Collections.emptyList());
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);

            return true;
        } catch (UnsupportedJwtException | MalformedJwtException e) {   //형식이 잘못되었거나, 지원되지 않는 형식
            log.error("JWT is not supported or has an incorrect format");
        } catch (SignatureException e) {    //서명이 올바르지 않을 때
            log.error("JWT signature validation failed");
        } catch (ExpiredJwtException e) {   //만료
            log.error("JWT is expired");
        } catch (IllegalArgumentException e) {  //토큰이 비어있거나, 공백이 들었을 때
            log.error("JWT is null or empty or only white space");
        } catch (Exception e) { //이외의 예외
            log.error("JWT Exception other than the above cases");
        }

        return false;
    }
}
