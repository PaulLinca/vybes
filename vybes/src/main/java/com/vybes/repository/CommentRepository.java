package com.vybes.repository;

import com.vybes.model.Comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPostId(Long vybeId);

    @Modifying
    @Query(
            "DELETE FROM Comment c WHERE c.id = :commentId AND c.post.id = :postId AND c.user.userId = :userId")
    int deleteByCommentIdAndPostIdAndUserId(
            @Param("commentId") Long commentId,
            @Param("postId") Long postId,
            @Param("userId") Long userId);
}
