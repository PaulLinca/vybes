package com.vybes.repository;

import com.vybes.model.VybesUser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<VybesUser, Integer> {
    Optional<VybesUser> findByUsername(String username);

    Optional<VybesUser> findByEmail(String email);
}
