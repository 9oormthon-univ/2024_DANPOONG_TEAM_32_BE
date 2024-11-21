package com.danpoong.onchung.domain.chat_gpt.repository;

import com.danpoong.onchung.domain.chat_gpt.domain.Word;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WordRepository extends JpaRepository<Word, Long> {
    List<Word> findByCategory(String category);
}
