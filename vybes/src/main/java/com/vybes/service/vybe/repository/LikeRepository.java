package com.vybes.service.vybe.repository;

import com.vybes.service.vybe.entity.Like;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    List<Like> findByVybeId(Long vybeId);
}
