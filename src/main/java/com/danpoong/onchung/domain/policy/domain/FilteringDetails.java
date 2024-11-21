package com.danpoong.onchung.domain.policy.domain;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class FilteringDetails {
    private String ageInfo; // 연령 정보
    private String employmentStatus; // 취업상태내용
    private String specializationField; // 특화분야내용
    private String educationRequirement; // 학력요건내용
}
