package com.danpoong.onchung.domain.word.domain.enums;

import lombok.Getter;

@Getter
public enum WordCategory {
    PUBLIC("공공"),
    FINANCE("금융"),
    ECONOMY("경제"),
    SOCIETY("사회");

    private final String korean;

    WordCategory(String korean) {
        this.korean = korean;
    }

    public String getKorean() {
        return korean;
    }

    public static WordCategory checkCategory(String korean) {
        for (WordCategory category : WordCategory.values()) {
            if (category.getKorean().equalsIgnoreCase(korean)) {
                return category;
            }
        }

        throw new IllegalArgumentException("유효하지 않은 카테고리");
    }

    @Override
    public String toString() {
        return korean;
    }
}
