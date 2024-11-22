package com.danpoong.onchung.global.security.jwt.repository;

import com.danpoong.onchung.global.security.jwt.domain.Token;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<Token, Long> {
}
