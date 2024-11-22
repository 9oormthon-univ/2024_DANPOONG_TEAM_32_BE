package com.danpoong.onchung.domain.word.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Table(name = "word")
public class Word {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "term")
    private String term;

    @Column(name = "category")
    private String category;

    @Column(name = "description")
    private String description;

    @Column(name = "example")
    private String example;

    @Column(name = "relatedWelfare")
    private String relatedWelfare;

    public Word() {
    }

    @Builder
    public Word(String category, String term, String description, String example, String relatedWelfare) {
        this.category = category;
        this.term = term;
        this.description = description;
        this.example = example;
        this.relatedWelfare = relatedWelfare;

    }
}
