package com.danpoong.onchung.domain.policy.domain.enums;

import lombok.Getter;

@Getter
public enum PolicyCategory {
    Employment("23010"), // 일자리 분야
    Housing("23020"), // 주거 분야
    Education("23030"), //교육 분야
    WelfareCulture("23040"), // 복지, 문화 분야
    ParticipationRights("23050"); // 참여, 권리 분야

    private final String code;

    PolicyCategory(String code) {
        this.code = code;
    }

    public static PolicyCategory findCategory(String code) {
        for (PolicyCategory category : PolicyCategory.values()) {
            if (category.getCode().equals(code)) {
                return category;
            }
        }

        throw new IllegalArgumentException("Invalid category code: " + code);
    }
}
