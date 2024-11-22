package com.danpoong.onchung.domain.word.service;

import com.danpoong.onchung.domain.word.domain.Word;
import com.danpoong.onchung.domain.word.dto.WordResponseDto;
import com.danpoong.onchung.domain.word.repository.WordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class WordService {
    private final WordRepository wordRepository;

    public List<WordResponseDto> getWordsByCategory(String category) {
        // 카테고리 목록
        List<String> validCategories = List.of("공공", "금융", "경제", "사회");

        // 유효한 카테고리인지 확인
        if (!validCategories.contains(category)) {
            throw new RuntimeException("유효하지 않은 카테고리입니다.");
        }

        // 카테고리에 해당하는 단어 조회
        List<Word> words = wordRepository.findByCategory(category);

        if (words.isEmpty()) {
            throw new RuntimeException("해당 카테고리의 단어가 존재하지 않습니다.");
        }

        return words.stream()
                .map(word -> WordResponseDto.builder()
                        .term(word.getTerm())
                        .category(word.getCategory())
                        .description(word.getDescription())
                        .example(word.getExample())
                        .relatedWelfare(word.getRelatedWelfare())
                        .build())
                .collect(Collectors.toList());
    }
}