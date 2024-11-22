package com.danpoong.onchung.domain.word.controller;

import com.danpoong.onchung.domain.word.dto.WordResponseDto;
import com.danpoong.onchung.domain.word.repository.WordRepository;
import com.danpoong.onchung.domain.word.service.WordService;
import com.danpoong.onchung.global.template.ResponseTemplate;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "Word Controller", description = "Find By Category")
@RequestMapping("/word")
public class WordController {
    private final WordService wordService;

    @GetMapping("/{category}")
    public ResponseTemplate<List<WordResponseDto>> getWords(@PathVariable("category") String category) {
        List<WordResponseDto> words = wordService.getWordsByCategory(category);
        return new ResponseTemplate<>(HttpStatus.OK, "단어 카테고리 조회 성공", words);
    }
}
