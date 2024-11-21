package com.danpoong.onchung.domain.policy.service;

import com.danpoong.onchung.domain.policy.domain.Policy;
import com.danpoong.onchung.domain.policy.dto.PolicyResponseDto;
import com.danpoong.onchung.domain.policy.repository.PolicyRepository;
import com.danpoong.onchung.domain.public_office.domain.PublicOffice;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PolicyService {
    private final PolicyRepository policyRepository;

    public PolicyResponseDto getPolicy(Long policyId) {
        Policy policy = policyRepository.findById(policyId).orElseThrow(() -> new RuntimeException("해당 ID의 정책이 존재하지 않습니다."));

        return PolicyResponseDto.builder()
                .policyId(policy.getId())
                .policyName(policy.getName())
                .policyIntroduction(policy.getIntroduction())
                .supportDetails(policy.getSupportDetails())
                .supportScale(policy.getSupportScale())
                .supportTarget(policy.getSupportTarget())
                .ageInfo(policy.getFilteringDetails().getAgeInfo())
                .applicationPeriod(policy.getApplicationPeriod())
                .applicationProcedure(policy.getApplicationProcedure())
                .requiredDocuments(policy.getRequiredDocuments())
                .applicationSite(policy.getApplicationSite())
                .publicOffice(getPublicOfficeName(policy.getPublicOffices()))
                .contactPhoneNumber(policy.getContactInfo())
                .build();
    }

    private List<String> getPublicOfficeName(List<PublicOffice> publicOffices) {
        List<String> publicOfficeNames = new ArrayList<>();

        for (PublicOffice publicOffice : publicOffices) {
            publicOfficeNames.add(publicOffice.getName());
        }

        return publicOfficeNames;
    }
}
