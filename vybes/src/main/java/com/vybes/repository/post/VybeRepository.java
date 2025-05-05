package com.vybes.repository.post;

import com.vybes.model.Vybe;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VybeRepository extends JpaRepository<Vybe, Long> {}
