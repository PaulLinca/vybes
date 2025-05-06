package com.vybes.repository;

import com.vybes.model.PostLike;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    List<PostLike> findByPostId(Long postId);

    List<PostLike> findByPostIdAndUser_UserId(Long postId, Long userId);

    List<PostLike> deleteByPostIdAndUser_UserId(Long postId, Long userId);
}
