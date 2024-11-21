package com.danpoong.onchung.domain.policy.repository;

import com.danpoong.onchung.domain.policy.domain.Policy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PolicyRepository extends JpaRepository<Policy, Long> {
}
