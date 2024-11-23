package com.danpoong.onchung.domain.word.controller;

import com.danpoong.onchung.domain.word.dto.WordResponseDto;
import com.danpoong.onchung.domain.word.dto.WordSummaryResponseDto;
import com.danpoong.onchung.domain.word.service.WordService;
import com.danpoong.onchung.global.template.ResponseTemplate;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "Word Controller", description = "Find By Category")
@RequestMapping("/api/word")
public class WordController {
    private final WordService wordService;

    @Operation(summary = "카테고리 별 단어 조회", description = "한 페이지의 데이터 개수 설정 가능")
    @GetMapping("/category/{page_num}")
    public ResponseTemplate<Page<WordSummaryResponseDto>> getWords(
//            @AuthenticationPrincipal Long userId,
            @RequestParam String category,
            @PathVariable("page_num") int pageNum
    ) {
        return new ResponseTemplate<>(HttpStatus.OK, "단어 카테고리 조회 성공", wordService.getWordsByCategory(category, pageNum));
    }

    @Operation(summary = "단어 상세 조회")
    @GetMapping("/{word_id}")
    public ResponseTemplate<WordResponseDto> getWord(@PathVariable("word_id") Long wordId) {
        return new ResponseTemplate<>(HttpStatus.OK, "단어 상세 조회 성공", wordService.getWord(wordId));
    }

//    @Operation(summary = "단어 북마크", description = "만약 기존에 북마크에 있다면 삭제, 아니라면 추가")
//    @PatchMapping("/bookmark/{word_id}")
//    public ResponseTemplate<?> favoriteWord(
//            @AuthenticationPrincipal Long userId,
//            @PathVariable("word_id") Long wordId
//    ) {
//        wordService.favoriteWord(userId, wordId);
//        return new ResponseTemplate<>(HttpStatus.OK, "북마크 추가 / 삭제 성공");
//    }
//
//    @Operation(summary = "단어 북마크 조회")
//    @GetMapping("/book-mark/{page_num}")
//    public ResponseTemplate<Page<WordSummaryResponseDto>> getBookmarks(
//            @AuthenticationPrincipal Long userId,
//            @PathVariable("page_num") int pageNum
//    ) {
//        return new ResponseTemplate<>(HttpStatus.OK, "북마크 단어 조회 성공", wordService.getBookmarkedWords(userId, pageNum));
//    }

    @Operation(summary = "단어 검색", description = "type에는 카테고리 종류 중 하나 / 공백 중 하나가 들어갈 수 있다.")
    @GetMapping("/search")
    public ResponseTemplate<WordSummaryResponseDto> searchWord(
//            @AuthenticationPrincipal Long userId,
            @RequestParam String type,
            @RequestParam String word
    ) {
        return new ResponseTemplate<>(HttpStatus.OK, "용어 검색 성공", wordService.searchWord(type, word));
    }
}
