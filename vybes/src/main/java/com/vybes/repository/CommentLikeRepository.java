package com.vybes.repository;

import com.vybes.model.CommentLike;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
    @Query(
            "SELECT l FROM CommentLike l WHERE l.comment.id = :commentId AND l.user.userId = :userId")
    CommentLike findByCommentIdAndUserId(
            @Param("commentId") Long commentId, @Param("userId") Long userId);

    @Modifying
    @Query("DELETE FROM CommentLike l WHERE l.comment.id = :commentId AND l.user.userId = :userId")
    int deleteByCommentIdAndUserId(
            @Param("commentId") Long commentId, @Param("userId") Long userId);
}
