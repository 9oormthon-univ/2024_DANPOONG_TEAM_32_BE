package com.danpoong.onchung.domain.word.service;

import com.danpoong.onchung.domain.user.domain.UserInfo;
import com.danpoong.onchung.domain.user.exception.UserNotFoundException;
import com.danpoong.onchung.domain.user.repository.UserInfoRepository;
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
    private final UserInfoRepository userInfoRepository;

    private static final int WORD_PAGE_SIZE = 10;

    public Page<WordSummaryResponseDto> getWordsByCategory(String category, Long id, int page) {
        Page<Word> words = wordRepository.findWordsByCategoryWithBookmarkFirst(
                WordCategory.checkCategory(category),
                id,
                PageRequest.of(page, WORD_PAGE_SIZE)
        );

        return words.map(word -> {
            boolean isBookmarked = false;

            if (id != null) {
                UserInfo userInfo = userInfoRepository.findById(id)
                        .orElseThrow(() -> new UserNotFoundException("해당 ID의 사용자가 존재하지 않습니다.");

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
        Word word = wordRepository.findById(wordId).orElseThrow(() -> new UserNotFoundException("해당 ID의 단어가 존재하지 않습니다."));

        return WordResponseDto.builder()
                .term(word.getTerm())
                .category(word.getCategory().toString())
                .description(word.getDescription())
                .example(word.getExample())
                .relatedWelfare(word.getRelatedWelfare())
                .build();
    }

    @Transactional
    public void favoriteWord(Long id, Long wordId) {
        UserInfo userInfo = userInfoRepository.findById(id).orElseThrow(() -> new UserNotFoundException("사용자가 존재하지 않습니다."));
        Word word = wordRepository.findById(wordId).orElseThrow(() -> new RuntimeException("해당 ID의 단어가 존재하지 않습니다."));

        boolean isPresent = userInfo.getFavoriteWords().contains(word);

        if (!isPresent) {
            userInfo.addFavoriteWord(word);
        } else {
            userInfo.removeFavoriteWord(word);
        }
    }

    public Page<WordSummaryResponseDto> getBookmarkedWords(Long id, int page) {
        UserInfo userInfo = userInfoRepository.findById(id).orElseThrow(() -> new UserNotFoundException("해당 ID의 사용자가 존재하지 않습니다."));
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

    public WordSummaryResponseDto searchWord(String type, String term, Long id) {
        UserInfo userInfo = userInfoRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("해당 ID의 사용자가 존재하지 않습니다."));

        Word word = wordRepository.findByTerm(term).orElse(null);
        if (word == null) {
            return WordSummaryResponseDto.empty();
        }

        // 북마크 검색
        if (type.equals("북마크")) {
            return searchBookmark(userInfo, word);
        }

        // 전체 검색
        if (type.isEmpty()) {
            return searchAll(word, userInfo);
        }

        // 카테고리 검색
        return searchByCategory(type, word, userInfo);
    }

    // 북마크 내 검색
    private WordSummaryResponseDto searchBookmark(UserInfo userInfo, Word word) {
        if (userInfo.getFavoriteWords().contains(word)) {
            return createWordSummaryResponse(word, true);
        }
        return WordSummaryResponseDto.empty();
    }

    // 전체 검색
    private WordSummaryResponseDto searchAll(Word word, UserInfo userInfo) {
        boolean isBookmark = userInfo.getFavoriteWords().contains(word);
        return createWordSummaryResponse(word, isBookmark);
    }


    // 카테고리 내 검색
    private WordSummaryResponseDto searchByCategory(String type, Word word, UserInfo userInfo) {
        WordCategory wordCategoryEng = WordCategory.checkCategory(type);
        List<Word> wordList = wordRepository.findAllByCategory(wordCategoryEng);

        if (wordList.contains(word)) {
            return createWordSummaryResponse(word, false);
        }

        return WordSummaryResponseDto.empty();

//        if (wordList.contains(word) && userInfo.getFavoriteWords().contains(word)) {
//            return createWordSummaryResponse(word, true);
//        }
//
//        return WordSummaryResponseDto.empty();
    }

    // 응답 객체 생성
    private WordSummaryResponseDto createWordSummaryResponse(Word word, boolean isBookmark) {
        return WordSummaryResponseDto.builder()
                .wordId(word.getId())
                .term(word.getTerm())
                .isBookmark(isBookmark)
                .relatedWelfare(word.getRelatedWelfare())
                .build();
    }
}