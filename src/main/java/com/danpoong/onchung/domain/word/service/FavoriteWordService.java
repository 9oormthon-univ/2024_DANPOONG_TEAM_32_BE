package com.danpoong.onchung.domain.word.service;

import com.danpoong.onchung.domain.word.domain.FavoriteWord;
import com.danpoong.onchung.domain.word.repository.FavoriteWordRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FavoriteWordService {
    private final FavoriteWordRepository favoriteWordRepository;

    public void addFavoriteWord(Long userId, Long wordId) {
        FavoriteWord favoriteWord = new FavoriteWord(userId, wordId);
        favoriteWordRepository.save(favoriteWord);
    }

    public List<FavoriteWord> getFavoriteWords(Long userId) {
        return favoriteWordRepository.findByUserId(userId);
    }

    @Transactional
    public void removeFavoriteWord(Long userId, Long wordId) {
        favoriteWordRepository.deleteByUserIdAndWordId(userId, wordId);
    }
}
