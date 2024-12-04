package com.danpoong.onchung.domain.policy.service;

import com.danpoong.onchung.domain.policy.domain.Policy;
import com.danpoong.onchung.domain.policy.domain.enums.PolicyCategory;
import com.danpoong.onchung.domain.policy.domain.enums.PolicyPath;
import com.danpoong.onchung.domain.policy.dto.PolicyPathRequestDto;
import com.danpoong.onchung.domain.policy.dto.PolicyPathResponseDto;
import com.danpoong.onchung.domain.policy.dto.PolicyResponseDto;
import com.danpoong.onchung.domain.policy.dto.RecommendResponseDto;
import com.danpoong.onchung.domain.policy.repository.PolicyRepository;
import com.danpoong.onchung.domain.public_office.domain.PublicOffice;
import com.danpoong.onchung.domain.user.domain.UserInfo;
import com.danpoong.onchung.domain.user.exception.UserNotFoundException;
import com.danpoong.onchung.domain.user.repository.UserInfoRepository;
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
    private final PolicyRepository policyRepository;
    private final UserInfoRepository userInfoRepository;

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

    public PolicyPathResponseDto makePolicyPath(Long userId, PolicyPathRequestDto requestDto) {
        UserInfo userInfo = userInfoRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("해당 ID의 유저 정보를 찾을 수 없습니다."));
        userInfo.updateBirthDate(requestDto.birthDate());

        PolicyPath tempPolicyPath = determinePolicyPath(requestDto);

        userInfo.addPolicyPath(tempPolicyPath);

        return PolicyPathResponseDto.builder()
                .policyPathNum(tempPolicyPath.getNumber())
                .build();
    }

    public List<PolicyPath> getPolicyPaths(Long userId) {
        UserInfo userInfo = userInfoRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("해당 ID의 유저 정보를 찾을 수 없습니다."));
        return userInfo.getPolicyPaths();
    }

    private PolicyPath determinePolicyPath(PolicyPathRequestDto requestDto) {
        String interestTopic = requestDto.interestTopic();
        String educationStatus = requestDto.educationStatus();

        if (interestTopic.contains("일자리")) {
            return educationStatus.equals("고교 졸업") ? PolicyPath.공정한근로 : PolicyPath.커리어;
        }

        if (interestTopic.contains("주거")) {
            return educationStatus.equals("고졸 예정") ? PolicyPath.첫보금자리 : PolicyPath.자취생활;
        }

        if (interestTopic.contains("교육")) {
            return educationStatus.equals("고졸 예정") ? PolicyPath.미래를향한 : PolicyPath.예비창업자;
        }

        if (interestTopic.contains("복지")) {
            return educationStatus.equals("대졸 예정") ? PolicyPath.쉼표 : PolicyPath.문화;
        }

        return educationStatus.equals("대졸 예정") ? PolicyPath.권리 : PolicyPath.함께;
    }

    public List<RecommendResponseDto> recommendPolicy(int cardNum) {
        PolicyCategory category = switchIntToCategory(cardNum);
        String education = switchIntToEdu(cardNum);
        String employment = switchIntToEmpoly(cardNum);

        return policyRepository.findAll().stream()
                .filter(policy -> policy.getCategory().equals(category))
                .filter(policy -> matchesEmploymentStatus(policy, employment))
                .filter(policy -> matchesEducationRequirement(policy, education))
                .map(this::changeToRecommendResponseDto)
                .limit(5)
                .toList();
    }

    private RecommendResponseDto changeToRecommendResponseDto(Policy policy) {
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

    private PolicyCategory switchIntToCategory(int cardNum) {
        return switch (cardNum) {
            case 2, 3 -> PolicyCategory.EMPLOYMENT;
            case 4, 5 -> PolicyCategory.HOUSING;
            case 6, 1 -> PolicyCategory.EDUCATION;
            case 7, 8 -> PolicyCategory.WELFARE_CULTURE;
            case 9, 10 -> PolicyCategory.PARTICIPATION_RIGHTS;
            default -> null;
        };
    }

    private String switchIntToEdu(int cardNum) {
        return switch (cardNum) {
            case 4, 6 -> "고졸 예정";
            case 2, 7 -> "고교 졸업";
            case 1, 5, 9 -> "대학 재학";
            case 3, 8, 10 -> "대학 졸업";
            default -> "제한없음";
        };
    }

    private String switchIntToEmpoly(int cardNum) {
        return switch (cardNum) {
            case 2 -> "단기근로자";
            case 3, 8 -> "재직자";
            case 4 -> "사회초년생";
            case 6 -> "직업훈련생";
            case 1 -> "예비창업자";
            default -> "제한없음";
        };
    }

    private boolean matchesEmploymentStatus(Policy policy, String employment) {
        String employmentStatus = policy.getFilteringDetails().getEmploymentStatus();
        return employmentStatus.contains(employment) ||
                employmentStatus.contains("제한없음") ||
                employmentStatus.contains("-");
    }

    private boolean matchesEducationRequirement(Policy policy, String education) {
        String educationRequirement = policy.getFilteringDetails().getEducationRequirement();
        return educationRequirement.contains(education) ||
                educationRequirement.contains("제한없음") ||
                educationRequirement.contains("-");
    }
}
