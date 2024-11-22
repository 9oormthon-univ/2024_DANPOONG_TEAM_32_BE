package com.danpoong.onchung.domain.auth.dto;

import lombok.Builder;

@Builder
public record AccessTokenDto(
        String accessToken
) {
}
