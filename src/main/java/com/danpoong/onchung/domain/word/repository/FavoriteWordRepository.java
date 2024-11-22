package com.danpoong.onchung.domain.word.repository;

import com.danpoong.onchung.domain.word.domain.FavoriteWord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoriteWordRepository extends JpaRepository<FavoriteWord, Long> {
    List<FavoriteWord> findByUserId(Long userId);
    void deleteByUserIdAndWordId(Long userId, Long wordId);
}
