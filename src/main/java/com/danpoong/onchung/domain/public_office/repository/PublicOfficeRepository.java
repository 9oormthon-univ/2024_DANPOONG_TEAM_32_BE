package com.danpoong.onchung.domain.public_office.repository;

import com.danpoong.onchung.domain.public_office.domain.PublicOffice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PublicOfficeRepository extends JpaRepository<PublicOffice, Long> {
    Optional<PublicOffice> findByName(String name);
}
