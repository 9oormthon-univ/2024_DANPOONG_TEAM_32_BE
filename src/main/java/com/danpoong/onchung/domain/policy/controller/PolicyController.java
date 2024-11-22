package com.danpoong.onchung.domain.policy.controller;

import com.danpoong.onchung.domain.policy.dto.PolicyResponseDto;
import com.danpoong.onchung.domain.policy.service.PolicyService;
import com.danpoong.onchung.global.template.ResponseTemplate;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/policy")
public class PolicyController {
    private final PolicyService policyService;

    @Operation(summary = "정책 단일 조회", description = "조회 시 최근 본 정책에 자동 추가")
    @GetMapping("/{policy_id}")
    public ResponseTemplate<PolicyResponseDto> getPolicy(@PathVariable("policy_id") Long policyId, HttpServletRequest request, HttpServletResponse response) {
        return new ResponseTemplate<>(HttpStatus.OK, "정책 단일 조회 성공", policyService.getPolicy(policyId, request, response));
    }

    @Operation(summary = "정책 즐겨찾기", description = "정책이 존재하면 삭제, 아니라면 추가")
    @PatchMapping("/favorite/{policy_id}")
    public ResponseTemplate<?> favoritePolicy(
            @AuthenticationPrincipal Long userId,
            @PathVariable("policy_id") Long policyId
    ) {
        policyService.favoritePolicy(userId, policyId);
        return new ResponseTemplate<>(HttpStatus.OK, "즐겨찾기 추가 / 삭제 성공");
    }

}
