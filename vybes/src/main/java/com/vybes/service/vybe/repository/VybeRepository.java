package com.vybes.service.vybe.repository;

import com.vybes.service.vybe.entity.Vybe;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VybeRepository extends JpaRepository<Vybe, Long> {}
