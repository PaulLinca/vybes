package com.vybes.service.user.repository;

import com.vybes.service.user.model.VybesUser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<VybesUser, Integer> {
    Optional<VybesUser> findByUsername(String username);
    Optional<VybesUser> findByUserId(Long userId);
}
