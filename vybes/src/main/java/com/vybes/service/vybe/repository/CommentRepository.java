package com.vybes.service.vybe.repository;

import com.vybes.service.vybe.entity.Comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByVybeId(Long vybeId);
}
