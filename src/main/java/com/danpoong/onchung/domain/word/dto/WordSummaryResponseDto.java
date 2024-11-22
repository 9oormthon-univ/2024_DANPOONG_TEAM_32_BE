package com.danpoong.onchung.domain.word.dto;

import lombok.Builder;

@Builder
public record WordSummaryResponseDto(
        Long wordId,
        String term,
        Boolean isBookmark,
        String relatedWelfare
) {
    public static WordSummaryResponseDto empty() {
        return new WordSummaryResponseDto(null, "", false, "");
    }
}
