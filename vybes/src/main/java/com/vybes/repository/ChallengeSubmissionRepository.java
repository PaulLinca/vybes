package com.vybes.repository;

import com.vybes.model.ChallengeOption;

import com.vybes.model.ChallengeSubmission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChallengeSubmissionRepository extends JpaRepository<ChallengeSubmission, Long> {}
