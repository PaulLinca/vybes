package com.vybes.service.post;

import com.vybes.model.AlbumReview;
import com.vybes.repository.post.AlbumReviewRepository;

import jakarta.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class AlbumReviewService {

    private final AlbumReviewRepository albumReviewRepository;

    public AlbumReviewService(AlbumReviewRepository albumReviewRepository) {
        this.albumReviewRepository = albumReviewRepository;
    }

    public AlbumReview createAlbumReview(AlbumReview albumReview) {
        albumReviewRepository.save(albumReview);
        return albumReview;
    }

    public Page<AlbumReview> getAlbumReviewsPaginated(
            int page, int size, String sort, String direction) {
        Sort.Direction sortDirection = Sort.Direction.fromString(direction);
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sort));
        return albumReviewRepository.findAll(pageable);
    }
}
