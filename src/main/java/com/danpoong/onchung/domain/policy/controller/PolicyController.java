package com.danpoong.onchung.domain.policy.controller;

import com.danpoong.onchung.domain.policy.dto.PolicyResponseDto;
import com.danpoong.onchung.domain.policy.service.PolicyService;
import com.danpoong.onchung.global.template.ResponseTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/policy")
public class PolicyController {
    private final PolicyService policyService;

    @GetMapping("/{policy_id}")
    public ResponseTemplate<PolicyResponseDto> getPolicy(@PathVariable("policy_id") Long policyId) {
        return new ResponseTemplate<>(HttpStatus.OK, "정책 단일 조회 성공", policyService.getPolicy(policyId));
    }


}
