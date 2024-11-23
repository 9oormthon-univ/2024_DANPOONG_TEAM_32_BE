package com.danpoong.onchung.domain.user.domain;

import com.danpoong.onchung.domain.policy.domain.Policy;
import com.danpoong.onchung.domain.public_office.domain.PublicOffice;
import com.danpoong.onchung.domain.welfare_card.domain.enums.WelfareCard;
import com.danpoong.onchung.domain.word.domain.Word;
import com.danpoong.onchung.global.security.jwt.domain.Token;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
public class UserInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_info_id")
    private Long id;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "user_login_id")
    private String userLoginId;

    @Enumerated(EnumType.STRING)
    @Column(name = "welfare_card")
    private WelfareCard welfareCard;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recent_public_office_id")
    private PublicOffice recentPublicOffice;

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

    @OneToOne(mappedBy = "userInfo", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Token token;

    @Builder
    public UserInfo(String userName, String userLoginId, Token token) {
        this.userName = userName;
        this.userLoginId = userLoginId;
        this.token = token;
    }

    public void updateWelfareCard(WelfareCard welfareCard) {
        this.welfareCard = welfareCard;
    }

    public void addFavoritePolicy(Policy policy) {
        favoritePolicies.add(policy);
    }
    public void removeFavoritePolicy(Policy policy) {
        favoritePolicies.remove(policy);
    }

    public void addFavoriteWord(Word word) {
        favoriteWords.add(word);
    }
    public void removeFavoriteWord(Word word) {
        favoriteWords.remove(word);
    }

    // 관공서 재설정에서 사용
    public void updateRecentPublicOffice(PublicOffice recentPublicOffice) {
        if (this.recentPublicOffice.equals(recentPublicOffice)) {
            this.recentPublicOffice = recentPublicOffice;
        }
    }
}
