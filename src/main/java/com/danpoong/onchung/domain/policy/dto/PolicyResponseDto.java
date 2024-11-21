package com.danpoong.onchung.domain.policy.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record PolicyResponseDto(
        Long policyId,
        String policyName,
        String policyIntroduction,
        String supportDetails,
        String supportScale,
        String supportTarget,
        String ageInfo,
        String applicationPeriod,
        String applicationProcedure,
        String requiredDocuments,
        String applicationSite,
        List<String> publicOffice,
        String contactPhoneNumber
) {
}
