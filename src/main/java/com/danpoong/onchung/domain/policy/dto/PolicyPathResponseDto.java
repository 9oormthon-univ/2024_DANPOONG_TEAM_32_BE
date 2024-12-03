package com.danpoong.onchung.domain.policy.dto;

import lombok.Builder;

@Builder
public record PolicyPathResponseDto(
        int policyPathNum
) {
}
