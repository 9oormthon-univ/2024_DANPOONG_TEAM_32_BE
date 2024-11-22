package com.danpoong.onchung.domain.word.controller;

import com.danpoong.onchung.domain.word.domain.FavoriteWord;
import com.danpoong.onchung.domain.word.service.FavoriteWordService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/favorite-word")
public class FavoriteWordController {
    private final FavoriteWordService favoriteWordService;

    public FavoriteWordController(FavoriteWordService favoriteWordService) {
        this.favoriteWordService = favoriteWordService;
    }

    @Operation(summary = "용어 북마크 등록", description = "용어 북마크에 등록하기")
    @PostMapping
    public ResponseEntity<Void> addFavoriteWord(@RequestParam Long userId, @RequestParam Long wordId) {
        favoriteWordService.addFavoriteWord(userId, wordId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "용어 북마크 조회", description = "북마크한 용어 조회")
    @GetMapping("/{userId}")
    public ResponseEntity<List<FavoriteWord>> getFavoriteWords(@RequestParam Long userId) {
        List<FavoriteWord> favoriteWords = favoriteWordService.getFavoriteWords(userId);
        return ResponseEntity.ok(favoriteWords);
    }

    @Operation(summary = "용어 북마크 삭제", description = "북마크한 용어 해제")
    @DeleteMapping("/delete")
    public ResponseEntity<Void> removeFavoriteWord(@RequestParam Long userId, @RequestParam Long wordId) {
        favoriteWordService.removeFavoriteWord(userId, wordId);
        return ResponseEntity.ok().build();
    }
}
