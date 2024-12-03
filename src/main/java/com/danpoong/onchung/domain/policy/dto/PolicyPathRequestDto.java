package com.danpoong.onchung.domain.policy.dto;

public record PolicyPathRequestDto(
        String birthDate,
        String educationStatus,
        String employmentStatus,
        String baseInfo,
        String interestTopic
) {
}
