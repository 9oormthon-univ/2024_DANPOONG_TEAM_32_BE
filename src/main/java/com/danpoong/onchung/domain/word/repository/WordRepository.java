package com.danpoong.onchung.domain.word.repository;

import com.danpoong.onchung.domain.word.domain.Word;
import com.danpoong.onchung.domain.word.domain.enums.WordCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WordRepository extends JpaRepository<Word, Long> {
    Page<Word> findByCategory(WordCategory category, Pageable pageable);
    List<Word> findAllByCategory(WordCategory category);
    Optional<Word> findByTerm(String term);

    @Query("SELECT w FROM Word w " +
            "LEFT JOIN UserInfo u ON u.id = :userId " +
            "LEFT JOIN u.favoriteWords fw ON fw = w " +
            "WHERE w.category = :category " +
            "ORDER BY CASE WHEN fw IS NOT NULL THEN 0 ELSE 1 END, w.id")
    Page<Word> findWordsByCategoryWithBookmarkFirst(@Param("category") WordCategory category,
                                                    @Param("userId") Long userId,
                                                    Pageable pageable);
}
