package com.danpoong.onchung.domain.policy.domain;

import com.danpoong.onchung.domain.policy.domain.enums.PolicyCategory;
import com.danpoong.onchung.domain.policy.domain.enums.PolicyCategoryConverter;
import com.danpoong.onchung.domain.public_office.domain.PublicOffice;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor
@Getter
public class Policy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "policy_id")
    private Long id;

    @Column(name = "policy_name")
    private String name;
    @Column(name = "policy_introduction")
    private String introduction; // 정책소개

    @Column(name = "support_details")
    private String supportDetails; // 지원내용
    @Column(name = "support_scale")
    private String supportScale; // 지원규모
    @Column(name = "support_target")
    private String supportTarget; // 지원대상

    @Column(name = "application_period")
    private String applicationPeriod; // 신청기간
    @Column(name = "application_procedure")
    private String applicationProcedure; // 신청절차
    @Column(name = "required_documents")
    private String requiredDocuments; // 제출서류내용
    private String applicationSite; // 신청사이트주소

    // 연락처
    @Column(name = "contact_info")
    private String contactInfo;

    // 필터링 시 사용 - 연령, 취업상태, 특화분야, 학력요건
    @Embedded
    private FilteringDetails filteringDetails;

    @Convert(converter = PolicyCategoryConverter.class)
    private PolicyCategory category;

    @ManyToMany
    @JoinTable(
            name = "policy_public_office",
            joinColumns = @JoinColumn(name = "policy_id"),
            inverseJoinColumns = @JoinColumn(name = "public_office_id")
    )
    private List<PublicOffice> publicOffices;

    @Builder
    public Policy(String name, String introduction, String supportDetails, String supportScale, String supportTarget, String applicationPeriod, String applicationProcedure, String requiredDocuments, String applicationSite, String contactInfo, FilteringDetails filteringDetails, String category, List<PublicOffice> publicOffices) {
        this.name = name;
        this.introduction = introduction;
        this.supportDetails = supportDetails;
        this.supportScale = supportScale;
        this.supportTarget = supportTarget;
        this.applicationPeriod = applicationPeriod;
        this.applicationProcedure = applicationProcedure;
        this.requiredDocuments = requiredDocuments;
        this.applicationSite = applicationSite;
        this.contactInfo = contactInfo;
        this.filteringDetails = filteringDetails;
        this.category = PolicyCategory.findCategory(category);
        this.publicOffices = publicOffices;
    }
}