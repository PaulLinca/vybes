package com.vybes.repository;

import com.vybes.model.Challenge;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

@Repository
public interface ChallengeRepository extends JpaRepository<Challenge, Long> {
    @Query("SELECT c FROM Challenge c WHERE NOT EXISTS (SELECT 1 FROM FeaturedChallenge fc WHERE fc.challenge = c)")
    List<Challenge> findAllNotFeatured();
}
