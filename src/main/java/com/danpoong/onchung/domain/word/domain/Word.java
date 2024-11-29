package com.danpoong.onchung.domain.word.domain;

import com.danpoong.onchung.domain.word.domain.enums.WordCategory;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;


@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "word")
public class Word {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "word_id")
    private Long id;

    @Column(name = "term")
    private String term;

    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private WordCategory category;

    @Column(name = "description")
    private String description;

    @Column(name = "example")
    private String example;

    @Column(name = "related_welfare")
    private String relatedWelfare;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Word word = (Word) o;
        return Objects.equals(id, word.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Builder
    public Word(String category, String term, String description, String example, String relatedWelfare) {
        this.category = WordCategory.checkCategory(category);
        this.term = term;
        this.description = description;
        this.example = example;
        this.relatedWelfare = relatedWelfare;
    }
}
