package com.vybes.repository;

import com.vybes.model.FcmToken;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FcmTokenRepository extends JpaRepository<FcmToken, Long> {
    Optional<FcmToken> findByToken(String token);

    Optional<FcmToken> findByUser_UserId(Long userUserId);

    List<FcmToken> findAll();
}
