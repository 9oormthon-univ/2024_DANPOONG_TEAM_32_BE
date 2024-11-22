package com.danpoong.onchung.domain.word.service;

import com.danpoong.onchung.domain.user.domain.UserInfo;
import com.danpoong.onchung.domain.user.repository.UserRepository;
import com.danpoong.onchung.domain.word.domain.Word;
import com.danpoong.onchung.domain.word.domain.enums.WordCategory;
import com.danpoong.onchung.domain.word.dto.WordResponseDto;
import com.danpoong.onchung.domain.word.dto.WordSummaryResponseDto;
import com.danpoong.onchung.domain.word.repository.WordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WordService {
    private final WordRepository wordRepository;
    private final UserRepository userRepository;

    private static final int WORD_PAGE_SIZE = 10;

    public Page<WordSummaryResponseDto> getWordsByCategory(Long userId, String category, int page) {
        Page<Word> words = wordRepository.findByCategory(WordCategory.checkCategory(category), PageRequest.of(page, WORD_PAGE_SIZE));

        return words.map(word -> {
            boolean isBookmarked = false;

            if (userId != null) {
                UserInfo userInfo = userRepository.findById(userId)
                        .orElseThrow(() -> new RuntimeException("해당 ID의 사용자가 존재하지 않습니다."));

                isBookmarked = userInfo.getFavoriteWords().contains(word);
            }

            return WordSummaryResponseDto.builder()
                    .wordId(word.getId())
                    .term(word.getTerm())
                    .isBookmark(isBookmarked)
                    .relatedWelfare(word.getRelatedWelfare())
                    .build();
        });
    }

    public WordResponseDto getWord(Long wordId) {
        Word word = wordRepository.findById(wordId).orElseThrow(() -> new RuntimeException("해당 ID의 단어가 존재하지 않습니다."));

        return WordResponseDto.builder()
                .term(word.getTerm())
                .category(word.getCategory().toString())
                .description(word.getDescription())
                .example(word.getExample())
                .relatedWelfare(word.getRelatedWelfare())
                .build();
    }

    @Transactional
    public void favoriteWord(Long userId, Long wordId) {
        UserInfo userInfo = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("사용자가 존재하지 않습니다."));
        Word word = wordRepository.findById(wordId).orElseThrow(() -> new RuntimeException("해당 ID의 단어가 존재하지 않습니다."));

        boolean isPresent = userInfo.getFavoriteWords().contains(wordId);

        if (!isPresent) {
            userInfo.addFavoriteWord(word);
        } else {
            userInfo.removeFavoriteWord(word);
        }
    }

    public Page<WordSummaryResponseDto> getBookmarkedWords(Long userId, int page) {
        UserInfo userInfo = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("해당 ID의 사용자가 존재하지 않습니다."));
        List<Word> wordList = userInfo.getFavoriteWords();

        int totalWords = wordList.size();
        int start = Math.min(page * WORD_PAGE_SIZE, totalWords);
        int end = Math.min((page + 1) * WORD_PAGE_SIZE, totalWords);

        List<Word> pagedWords = wordList.subList(start, end);
        Page<Word> pageResult = new PageImpl<>(pagedWords, PageRequest.of(page, WORD_PAGE_SIZE), totalWords);

        return pageResult.map(word -> WordSummaryResponseDto.builder()
                .wordId(word.getId())
                .term(word.getTerm())
                .isBookmark(true)
                .relatedWelfare(word.getRelatedWelfare())
                .build()
        );
    }
}