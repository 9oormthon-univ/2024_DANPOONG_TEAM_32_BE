package com.danpoong.onchung.domain.word.dto;

import lombok.Builder;

@Builder
public record WordResponseDto(
        String term,
        String category,
        String description,
        String example,
        String relatedWelfare
) {
}
