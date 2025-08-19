package com.vybes.repository;

import com.vybes.model.AlbumReview;
import com.vybes.model.VybesUser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlbumReviewRepository extends JpaRepository<AlbumReview, Long> {
    List<AlbumReview> findByUserAndAlbum_ExternalId(VybesUser user, String externalId);
}
