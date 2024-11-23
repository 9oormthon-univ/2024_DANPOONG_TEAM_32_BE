package com.danpoong.onchung.domain.word.domain.enums;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
public enum WordCategory {
    공공,
    금융,
    경제,
    사회;

    public static WordCategory checkCategory(String input) {
        for (WordCategory category : WordCategory.values()) {
            if (category.name().equals(input)) {
                log.info(category.name());
                return category;
            }
        }

        throw new IllegalArgumentException("유효하지 않은 카테고리");
    }

    @Override
    public String toString() {
        return name();
    }
}
