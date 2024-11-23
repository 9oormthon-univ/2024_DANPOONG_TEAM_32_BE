package com.danpoong.onchung.global.db.service;

import com.danpoong.onchung.domain.word.repository.WordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DataPrunerService {
    private final WordRepository wordRepository;

    @Transactional
    public void wordExampleAndRelatedWelfare() {
        wordRepository.findAll()
                .forEach(word -> {
                    if (word.getExample() != null) {
                        word.setExample(word.getExample().replaceAll(".*?: ", ""));
                    }

                    // "related_welfare" 필드에서 "어떤 단어: " 제거
                    if (word.getRelatedWelfare() != null) {
                        word.setRelatedWelfare(word.getRelatedWelfare().replaceAll(".*?: ", ""));
                    }

                    // 변경된 엔티티 저장
                    wordRepository.save(word);
                });
    }
}
