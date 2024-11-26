package com.danpoong.onchung.global.security.oauth.kakao;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class KakaoUserInfo {
    @JsonProperty("kakao_account")
    private KakaoAccount kakaoAccount;

    @JsonIgnoreProperties(ignoreUnknown = true)
    record KakaoAccount(String email, String name) {}

//    @JsonIgnoreProperties(ignoreUnknown = true)
//    record Profile(String nickname) {}

    public String getEmail() {
        return kakaoAccount.email;
    }

    public String getName() {
        return kakaoAccount.name;
    }

//    public String getNickname() {
//        return kakaoAccount.profile.nickname;
//    }
}