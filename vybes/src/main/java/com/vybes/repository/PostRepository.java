package com.vybes.repository;

import java.util.List;
import java.util.Optional;

import com.vybes.model.AlbumReview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.vybes.model.Post;
import com.vybes.model.VybesUser;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findAllByOrderByPostedDateDesc(Pageable pageable);

    Page<Post> findByUserOrderByPostedDateDesc(VybesUser user, Pageable pageable);

    Page<Post> findByUserInOrderByPostedDateDesc(List<VybesUser> users, Pageable pageable);

    @Query("SELECT a FROM AlbumReview a WHERE a.spotifyId = :spotifyId AND a.user = :user")
    Optional<AlbumReview> findAlbumReviewBySpotifyIdAndUser(
            @Param("spotifyId") String spotifyId,
            @Param("user") VybesUser user
    );
}
