package com.vybes.service.vybe.repository;

import com.vybes.service.vybe.entity.Comment;
import com.vybes.service.vybe.entity.Like;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByVybeId(Long vybeId);

    List<Comment> deleteByVybeIdAndUser_UserId(Long vybeId, Long userId);

    @Modifying
    @Query(
            "DELETE FROM Comment c WHERE c.id = :commentId AND c.vybe.id = :vybeId AND c.user.userId = :userId")
    int deleteByCommentIdAndVybeIdAndUserId(
            @Param("commentId") Long commentId,
            @Param("vybeId") Long vybeId,
            @Param("userId") Long userId);
}
