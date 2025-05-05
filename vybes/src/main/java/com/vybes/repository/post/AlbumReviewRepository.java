package com.vybes.repository.post;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.vybes.model.AlbumReview;

@Repository
public interface AlbumReviewRepository extends JpaRepository<AlbumReview, Long> {
    Page<AlbumReview> findAll(Pageable pageable);
}
