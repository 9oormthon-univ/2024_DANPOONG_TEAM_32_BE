package com.danpoong.onchung.domain.word.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
@Table(name = "favoriteWord")
public class FavoriteWord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long userId;

    @Column
    private Long wordId;

    public FavoriteWord() {}

    @Builder
    public FavoriteWord(Long userId, Long wordId) {
        this.userId = userId;
        this.wordId = wordId;
    }
}