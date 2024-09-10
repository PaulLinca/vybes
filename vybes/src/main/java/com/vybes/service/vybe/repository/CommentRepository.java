package com.vybes.service.vybe.repository;

import com.vybes.service.vybe.entity.Comment;
import com.vybes.service.vybe.entity.Like;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByVybeId(Long vybeId);

    List<Comment> deleteByVybeIdAndUser_UserId(Long vybeId, Long userId);
}
