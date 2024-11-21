package com.danpoong.onchung.domain.user.domain;

import com.danpoong.onchung.domain.policy.domain.Policy;
import com.danpoong.onchung.domain.welfare_card.domain.enums.WelfareCard;
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

    @ManyToMany
    @JoinTable(
            name = "user_favorite_policy",
            joinColumns = @JoinColumn(name = "user_info_id"),
            inverseJoinColumns = @JoinColumn(name = "policy_id")
    )
    private List<Policy> favoritePolicies = new ArrayList<>();

    @Builder
    public UserInfo(String userName, String userLoginId, WelfareCard welfareCard) {
        this.userName = userName;
        this.userLoginId = userLoginId;
        this.welfareCard = welfareCard;
    }

    public void addFavoritePolicy(Policy policy) {
        favoritePolicies.add(policy);
    }
    public void removeFavoritePolicy(Policy policy) {
        favoritePolicies.remove(policy);
    }
}
