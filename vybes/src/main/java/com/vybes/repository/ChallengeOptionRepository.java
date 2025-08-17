package com.vybes.repository;


import com.vybes.model.ChallengeOption;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChallengeOptionRepository extends JpaRepository<ChallengeOption, Long> {}
