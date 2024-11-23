package com.danpoong.onchung.domain.policy.service;

import com.danpoong.onchung.domain.policy.domain.Policy;
import com.danpoong.onchung.domain.policy.domain.enums.PolicyCategory;
import com.danpoong.onchung.domain.policy.dto.PolicyResponseDto;
import com.danpoong.onchung.domain.policy.dto.RecommendResponseDto;
import com.danpoong.onchung.domain.policy.repository.PolicyRepository;
import com.danpoong.onchung.domain.public_office.domain.PublicOffice;
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
//    private final UserRepository userRepository;

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

    public List<RecommendResponseDto> recommendPolicy(int cardNum) {
        return policyRepository.findAll().stream()
                .filter(p -> p.getCategory().equals(switchIntToCategory(cardNum)))
                .filter(p ->
                        p.getFilteringDetails().getEmploymentStatus().contains(switchIntToEmpoly(cardNum))
                        ||
                                p.getFilteringDetails().getEmploymentStatus().contains("제한없음")
                        ||
                                p.getFilteringDetails().getEmploymentStatus().contains("-")
                )
                .filter(p ->
                        p.getFilteringDetails().getEducationRequirement().contains(switchIntToEdu(cardNum))
                                ||
                                p.getFilteringDetails().getEducationRequirement().contains("제한없음")
                                ||
                                p.getFilteringDetails().getEducationRequirement().contains("-")
                )
                .map(this::recommendPolicy)
                .limit(5)
                .toList();
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

    private PolicyCategory switchIntToCategory(int cardNum) {
        switch (cardNum) {
            case 1, 2:
                return PolicyCategory.Employment;
            case 3, 4:
                return PolicyCategory.Housing;
            case 5, 6:
                return PolicyCategory.Education;
            case 7:
                return PolicyCategory.WelfareCulture;
            default:
                return null;
        }
    }

    private String switchIntToEdu(int cardNum) {
        switch (cardNum) {
            case 3, 5:
//                return EducationStatus.고졸_예정;
                return "고졸 예정";
            case 1:
//                return EducationStatus.고교_졸업;
                return "고교 졸업";
            case 4, 6:
//                return EducationStatus.대학_재학;
                return "대학 재학";
            case 2, 7:
//                return EducationStatus.대학_졸업;
                return "대학 졸업";
            default:
                return "제한없음";
        }
    }

    private String switchIntToEmpoly(int cardNum) {
        switch (cardNum) {
            case 1:
//                return EmploymentStatus.단기근로자;
                return "단기근로자";
            case 2, 7:
//                return EmploymentStatus.재직자_인턴;
                return "재직자";
            case 3, 4:
//                return EmploymentStatus.사회초년생;
                return "사회초년생";
            case 5:
//                return EmploymentStatus.직업훈련생;
                return "직업훈련생";
            case 6:
//                return EmploymentStatus.예비창업자;
                return "예비창업자";
            default:
                return "제한없음";
        }
    }

    private RecommendResponseDto recommendPolicy(Policy policy) {
        return RecommendResponseDto.builder()
                .policyId(policy.getId())
                .ageInfo(policy.getFilteringDetails().getAgeInfo())
                .policyName(policy.getName())
                .applicationPeriod(policy.getApplicationPeriod())
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
