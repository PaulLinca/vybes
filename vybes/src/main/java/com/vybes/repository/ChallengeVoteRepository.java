package com.vybes.repository;

import com.vybes.model.ChallengeVote;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChallengeVoteRepository extends JpaRepository<ChallengeVote, Long> {}
