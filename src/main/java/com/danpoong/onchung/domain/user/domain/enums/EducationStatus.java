package com.danpoong.onchung.domain.user.domain.enums;

import lombok.Getter;

@Getter
public enum EducationStatus {
    HIGH_SCHOOL_GRADUATE_EXPECTED("고졸 예정"),
    HIGH_SCHOOL_GRADUATED("고교 졸업"),
    UNIVERSITY_ENROLLED("대학 재학"),
    UNIVERSITY_GRADUATE_EXPECTED("대졸 예정"),
    UNIVERSITY_GRADUATED("대학 졸업");

    private final String korean;

    EducationStatus(String korean) {
        this.korean = korean;
    }

    public String getKorean() {
        return korean;
    }

    public static EducationStatus fromKorean(String korean) {
        for (EducationStatus status : values()) {
            if (status.getKorean().equals(korean)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown education status: " + korean);
    }
}
