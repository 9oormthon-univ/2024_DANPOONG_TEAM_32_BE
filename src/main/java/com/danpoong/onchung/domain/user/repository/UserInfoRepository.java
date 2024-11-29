package com.danpoong.onchung.domain.user.repository;

import com.danpoong.onchung.domain.user.domain.UserInfo;
import com.danpoong.onchung.domain.word.domain.Word;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {
    Optional<UserInfo> findByRefreshToken(String refreshToken);
    Optional<UserInfo> findByEmail(String email);

    @Query(value = "SELECT w FROM UserInfo u JOIN u.favoriteWords w WHERE u.id = :userId",
            countQuery = "SELECT COUNT(w) FROM UserInfo u JOIN u.favoriteWords w WHERE u.id = :userId")
    Page<Word> findFavoriteWordsByUserId(@Param("userId") Long userId, Pageable pageable);

//    @Query(value = "SELECT w FROM UserInfo u JOIN u.favoriteWords w WHERE u.id = :userId",
//            countQuery = "SELECT COUNT(w) FROM UserInfo u JOIN u.favoriteWords w WHERE u.id = :userId")
//    List<Word> findFavoriteWordsByUserId(@Param("userId") Long userId);
}
