package com.danpoong.onchung.domain.user.repository;

import com.danpoong.onchung.domain.user.domain.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {
    Optional<UserInfo> findByRefreshToken(String refreshToken);
    Optional<UserInfo> findByEmail(String email);
}
