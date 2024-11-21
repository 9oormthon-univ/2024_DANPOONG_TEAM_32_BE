package com.danpoong.onchung.domain.public_office.repository;

import com.danpoong.onchung.domain.public_office.domain.PublicOffice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PublicOfficeRepository extends JpaRepository<PublicOffice, Long> {
    Optional<PublicOffice> findByName(String name);

    @Query("SELECT po FROM PublicOffice po WHERE " +
            "CAST(po.longitude AS double) BETWEEN :leftBottomLongitude AND :rightTopLongitude AND " +
            "CAST(po.latitude AS double) BETWEEN :leftBottomLatitude AND :rightTopLatitude")
    List<PublicOffice> findInArea(
            @Param("leftBottomLongitude") double leftBottomLongitude,
            @Param("rightTopLongitude") double rightTopLongitude,
            @Param("leftBottomLatitude") double leftBottomLatitude,
            @Param("rightTopLatitude") double rightTopLatitude
    );
}
