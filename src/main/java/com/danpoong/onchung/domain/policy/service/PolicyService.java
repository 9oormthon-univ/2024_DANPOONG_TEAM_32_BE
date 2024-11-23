package com.danpoong.onchung.domain.policy.service;

import com.danpoong.onchung.domain.policy.domain.Policy;
import com.danpoong.onchung.domain.policy.dto.PolicyResponseDto;
import com.danpoong.onchung.domain.policy.repository.PolicyRepository;
import com.danpoong.onchung.domain.public_office.domain.PublicOffice;
import com.danpoong.onchung.domain.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PolicyService {
    private static final String RECENT_POLICY_COOKIE_NAME = "recentPolicyList";
    private static final int COOKIE_EXPIRE_SECONDS = 60 * 60 * 24 * 365 * 10; // 10년

    private final PolicyRepository policyRepository;
    private final UserRepository userRepository;

    public PolicyResponseDto getPolicy(Long policyId, HttpServletRequest request, HttpServletResponse response) {
        Policy policy = policyRepository.findById(policyId).orElseThrow(() -> new RuntimeException("해당 ID의 정책이 존재하지 않습니다."));

//        List<Long> recentPolicyList = CookieUtil.getLongListFromCookie(request, RECENT_POLICY_COOKIE_NAME);

//        if (recentPolicyList == null) {
//            recentPolicyList = new ArrayList<>();
//        }
//        CookieUtil.setLongListCookie(response, RECENT_POLICY_COOKIE_NAME, recentPolicyList, COOKIE_EXPIRE_SECONDS);

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

//    @Transactional
//    public void favoritePolicy(Long userId, Long policyId) {
//        UserInfo userInfo = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("사용자가 존재하지 않습니다."));
//
//        Policy policy = policyRepository.findById(policyId).orElseThrow(() -> new RuntimeException("해당 ID의 정책이 존재하지 않습니다."));
//
//        boolean isPresent = userInfo.getFavoritePolicies().contains(policy);
//
//        if (!isPresent) {
//            userInfo.addFavoritePolicy(policy);
//        } else {
//            userInfo.removeFavoritePolicy(policy);
//        }
//    }

    private List<String> getPublicOfficeName(List<PublicOffice> publicOffices) {
        List<String> publicOfficeNames = new ArrayList<>();

        for (PublicOffice publicOffice : publicOffices) {
            publicOfficeNames.add(publicOffice.getName());
        }

        return publicOfficeNames;
    }
}
