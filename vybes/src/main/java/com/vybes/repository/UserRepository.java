package com.vybes.repository;

import com.vybes.model.VybesUser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<VybesUser, Long> {
    Optional<VybesUser> findByEmail(String email);

    Optional<VybesUser> findByUserId(Long userId);

    Optional<VybesUser> findByUsername(String username);

    Optional<VybesUser> findByFirebaseUid(String firebaseUid);

    List<VybesUser> findByUsernameContainingIgnoreCase(String username);

    @Query("select size(u.followers) from VybesUser u where u.userId = :userId")
    Integer countFollowersByUserId(Long userId);

    @Query("select size(u.following) from VybesUser u where u.userId = :userId")
    Integer countFollowingByUserId(Long userId);
}
