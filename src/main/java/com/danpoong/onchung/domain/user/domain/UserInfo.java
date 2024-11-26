package com.danpoong.onchung.domain.user.domain;

import com.danpoong.onchung.domain.policy.domain.Policy;
import com.danpoong.onchung.domain.welfare_card.domain.enums.WelfareCard;
import com.danpoong.onchung.domain.word.domain.Word;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@ToString
public class UserInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_info_id")
    private Long id;

    @Column(name = "email")
    private String email;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "recent_public_office_area")
    private String recentPublicOfficeArea;

    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
            name = "user_welfare_card",
            joinColumns = @JoinColumn(name = "user_info_id")
    )
    @Column(name = "welfare_card")
    private List<WelfareCard> welfareCard = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "user_favorite_policy",
            joinColumns = @JoinColumn(name = "user_info_id"),
            inverseJoinColumns = @JoinColumn(name = "policy_id")
    )
    private List<Policy> favoritePolicies = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "user_favorite_word",
            joinColumns = @JoinColumn(name = "user_info_id"),
            inverseJoinColumns = @JoinColumn(name = "word_id")
    )
    private List<Word> favoriteWords = new ArrayList<>();

    @Column(name = "refresh_token")
    private String refreshToken;

    @Builder
    public UserInfo(String email, String nickname) {
        this.email = email;
        this.nickname = nickname;
    }

    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
