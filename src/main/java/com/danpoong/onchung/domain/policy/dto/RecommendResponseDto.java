package com.danpoong.onchung.domain.policy.dto;

import lombok.Builder;

@Builder
public record RecommendResponseDto(
        Long policyId,
        String policyName,
        String ageInfo,
        String applicationPeriod
) {
}
