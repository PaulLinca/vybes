package com.vybes.service.vybe.repository;

import com.vybes.service.vybe.entity.Like;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    List<Like> findByVybeId(Long vybeId);

    List<Like> findByVybeIdAndUser_UserId(Long vybeId, Long userId);

    List<Like> deleteByVybeIdAndUser_UserId(Long vybeId, Long userId);

    @Query("SELECT l FROM Like l WHERE l.comment.id = :commentId AND l.user.userId = :userId")
    Like findByCommentIdAndUserId(@Param("commentId") Long commentId,
                                   @Param("userId") Long userId);

    @Modifying
    @Query("DELETE FROM Like l WHERE l.comment.id = :commentId AND l.user.userId = :userId")
    int deleteByCommentIdAndUserId(@Param("commentId") Long commentId,
                                   @Param("userId") Long userId);
}
