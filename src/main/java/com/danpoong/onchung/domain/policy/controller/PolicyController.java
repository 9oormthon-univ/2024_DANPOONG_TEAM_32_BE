package com.danpoong.onchung.domain.policy.controller;

import com.danpoong.onchung.domain.policy.domain.enums.PolicyPath;
import com.danpoong.onchung.domain.policy.dto.PolicyPathRequestDto;
import com.danpoong.onchung.domain.policy.dto.PolicyPathResponseDto;
import com.danpoong.onchung.domain.policy.dto.PolicyResponseDto;
import com.danpoong.onchung.domain.policy.dto.RecommendResponseDto;
import com.danpoong.onchung.domain.policy.service.PolicyService;
import com.danpoong.onchung.global.template.ResponseTemplate;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/policy")
public class PolicyController {
    private final PolicyService policyService;

    @Operation(summary = "정책 상세 조회", description = "조회 시 최근 본 정책에 자동 추가")
    @GetMapping("/{policy_id}")
    public ResponseTemplate<PolicyResponseDto> getPolicy(@PathVariable("policy_id") Long policyId, HttpServletRequest request, HttpServletResponse response) {
        return new ResponseTemplate<>(HttpStatus.OK, "정책 단일 조회 성공", policyService.getPolicy(policyId, request, response));
    }

    @Operation(summary = "복지패스 발급", description = "반환 값은 피그마 사전 작업 페이지의 카드 순서와 동일(1~10)")
    @PostMapping("/path")
    public ResponseTemplate<PolicyPathResponseDto> makePolicyPath(
            @AuthenticationPrincipal Long userId,
            @RequestBody PolicyPathRequestDto requestDto
    ) {
        return new ResponseTemplate<>(HttpStatus.CREATED, "복지패스 발급 성공", policyService.makePolicyPath(userId, requestDto));
    }

    @Operation(summary = "발급 받은 복지패스 조회")
    @GetMapping("/path")
    public ResponseTemplate<List<PolicyPath>> getPolicyPath(@AuthenticationPrincipal Long userId) {
        return new ResponseTemplate<>(HttpStatus.OK, "발급한 복지패스 조회 성공", policyService.getPolicyPaths(userId));
    }

    @Operation(summary = "정책 추천")
    @GetMapping("/recommend")
    public ResponseTemplate<List<RecommendResponseDto>> recommend(@RequestParam int cardNum) {
        return new ResponseTemplate<>(HttpStatus.OK, "정책 추천 성공", policyService.recommendPolicy(cardNum));
    }

//    @Operation(summary = "정책 즐겨찾기", description = "정책이 존재하면 삭제, 아니라면 추가")
//    @PatchMapping("/favorite/{policy_id}")
//    public ResponseTemplate<?> favoritePolicy(
//            @AuthenticationPrincipal Long userId,
//            @PathVariable("policy_id") Long policyId
//    ) {
//        policyService.favoritePolicy(userId, policyId);
//        return new ResponseTemplate<>(HttpStatus.OK, "즐겨찾기 추가 / 삭제 성공");
//    }
}
