package com.danpoong.onchung.domain.word.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "favoriteWord")
public class FavoriteWord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long userId;

    @Column
    private Long wordId;

    @Builder
    public FavoriteWord(Long userId, Long wordId) {
        this.userId = userId;
        this.wordId = wordId;
    }
}