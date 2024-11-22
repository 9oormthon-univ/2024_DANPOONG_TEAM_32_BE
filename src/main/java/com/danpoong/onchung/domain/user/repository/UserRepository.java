package com.danpoong.onchung.domain.user.repository;

import com.danpoong.onchung.domain.user.domain.UserInfo;
import com.danpoong.onchung.global.security.jwt.domain.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserInfo, Long> {
    Optional<UserInfo> findByToken(Token token);
    Optional<UserInfo> findByUserLoginId(String loginId);
}
