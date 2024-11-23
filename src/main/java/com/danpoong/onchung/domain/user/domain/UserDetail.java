package com.danpoong.onchung.domain.user.domain;

import com.danpoong.onchung.domain.policy.domain.enums.PolicyCategory;
import com.danpoong.onchung.domain.user.domain.enums.EducationStatus;
import com.danpoong.onchung.domain.user.domain.enums.EmploymentStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
public class UserDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_detail_id")
    private Long id;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "education_status")
    private EducationStatus educationStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "empolyment_status")
    private EmploymentStatus employmentStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "interest_policy_category")
    private PolicyCategory interestPolicyCategory;
}
