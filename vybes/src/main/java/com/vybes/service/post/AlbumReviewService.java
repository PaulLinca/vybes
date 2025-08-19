package com.vybes.service.post;

import com.vybes.dto.TrackReviewDTO;
import com.vybes.dto.request.CreateAlbumReviewRequestDTO;
import com.vybes.model.*;
import com.vybes.repository.AlbumReviewRepository;
import com.vybes.repository.PostRepository;
import com.vybes.service.music.MusicService;

import jakarta.transaction.Transactional;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class AlbumReviewService {

    private final MusicService musicService;
    private final PostRepository postRepository;
    private final AlbumReviewRepository albumReviewRepository;

    public AlbumReview createAlbumReview(CreateAlbumReviewRequestDTO request, VybesUser user) {
        Album album = musicService.getOrCreateAlbum(request.getSpotifyAlbumId());

        AlbumReview albumReview =
                AlbumReview.builder()
                        .album(album)
                        .score(request.getScore())
                        .description(request.getDescription())
                        .user(user)
                        .postedDate(ZonedDateTime.now())
                        .comments(new ArrayList<>())
                        .likes(new ArrayList<>())
                        .build();

        albumReview.setTrackReviews(createTrackReviews(request.getTrackReviews(), albumReview));

        return postRepository.save(albumReview);
    }

    public AlbumReview getAlbumReview(Long albumReviewId) {
        return postRepository
                .findById(albumReviewId)
                .filter(AlbumReview.class::isInstance)
                .map(AlbumReview.class::cast)
                .orElseThrow(() -> new IllegalArgumentException("Album review not found"));
    }

    public List<AlbumReview> getAlbumReviewByAlbumId(String albumId, VybesUser user) {
        return albumReviewRepository.findByUserAndAlbum_ExternalId(user, albumId);
    }

    private List<TrackReview> createTrackReviews(
            List<TrackReviewDTO> trackReviewDTOs, AlbumReview albumReview) {
        return Optional.ofNullable(trackReviewDTOs).orElse(Collections.emptyList()).stream()
                .map(dto -> createTrackReview(dto, albumReview))
                .collect(Collectors.toList());
    }

    private TrackReview createTrackReview(TrackReviewDTO dto, AlbumReview albumReview) {
        Track track = musicService.getOrCreateTrack(dto.getSpotifyTrackId());

        return TrackReview.builder()
                .track(track)
                .rating(dto.getRating())
                .isFavorite(dto.isFavorite())
                .albumReview(albumReview)
                .build();
    }
}
