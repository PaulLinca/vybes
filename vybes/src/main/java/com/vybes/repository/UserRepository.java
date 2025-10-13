package com.vybes.repository;

import com.vybes.model.VybesUser;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<VybesUser, Long> {
    Optional<VybesUser> findByEmail(String email);

    Optional<VybesUser> findByUserId(Long userId);

    Optional<VybesUser> findByUsername(String username);

    Optional<VybesUser> findByFirebaseUid(String firebaseUid);
}
