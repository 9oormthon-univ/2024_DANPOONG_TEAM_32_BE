package com.danpoong.onchung.domain.policy.domain.enums;

import lombok.Getter;

@Getter
public enum PolicyCategory {
    EMPLOYMENT("23010", "일자리 분야"),
    HOUSING("23020", "주거 분야"),
    EDUCATION("23030", "교육 분야"),
    WELFARE_CULTURE("23040", "복지, 문화 분야"),
    PARTICIPATION_RIGHTS("23050", "참여, 권리 분야");

    private final String code;
    private final String displayName;

    PolicyCategory(String code, String displayName) {
        this.code = code;
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
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
