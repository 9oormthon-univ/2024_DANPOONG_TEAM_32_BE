package com.danpoong.onchung.domain.word.repository;

import com.danpoong.onchung.domain.word.domain.Word;
import com.danpoong.onchung.domain.word.domain.enums.WordCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WordRepository extends JpaRepository<Word, Long> {
    Page<Word> findByCategory(WordCategory category, Pageable pageable);
    List<Word> findAllByCategory(WordCategory category);
    Optional<Word> findByTerm(String term);
}
