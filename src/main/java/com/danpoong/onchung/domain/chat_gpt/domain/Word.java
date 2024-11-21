package com.danpoong.onchung.domain.chat_gpt.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Entity
@Table(name = "word")
public class Word {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String term;

    @Column
    private String category;

    @Column
    private String description;

    @Column
    private String example;

    @Column
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
