package com.vybes.controller;

import com.vybes.dto.AlbumReviewDTO;
import com.vybes.dto.PageResponse;
import com.vybes.dto.mapper.AlbumReviewMapper;
import com.vybes.dto.request.CreateAlbumReviewRequestDTO;
import com.vybes.service.review.album.AlbumReviewService;
import com.vybes.service.review.album.entity.AlbumReview;
import com.vybes.service.spotify.SpotifyService;
import com.vybes.service.spotify.model.entity.SpotifyAlbum;
import com.vybes.service.user.repository.ArtistRepository;
import com.vybes.service.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/album-reviews")
@RequiredArgsConstructor
public class AlbumReviewController {

    private final AlbumReviewService albumReviewService;
    private final AlbumReviewMapper albumReviewMapper;
    private final SpotifyService spotifyService;
    private final UserRepository userRepository;
    private final ArtistRepository artistRepository;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/post", produces = "application/json; charset=UTF-8")
    public AlbumReviewDTO postAlbumReview(@RequestBody CreateAlbumReviewRequestDTO request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        AlbumReview albumReview = albumReviewMapper.transform(request);
        albumReview.setComments(new ArrayList<>());
        albumReview.setLikes(new ArrayList<>());
        albumReview.setUser(userRepository.findByEmail(authentication.getName()).orElseThrow());

        SpotifyAlbum spotifyAlbum = spotifyService.getAlbum(request.getSpotifyAlbumId());
        albumReview.setSpotifyAlbumId(spotifyAlbum.getId());
        albumReview.setAlbumName(spotifyAlbum.getName());
        albumReview.setImageUrl(spotifyAlbum.getImageUrl());

        albumReview.setSpotifyArtists(
                spotifyAlbum.getArtists().stream()
                        .map(
                                a ->
                                        Optional.ofNullable(
                                                        artistRepository.findBySpotifyId(a.getId()))
                                                .orElse(
                                                        spotifyService.getArtistAsEntity(
                                                                a.getId())))
                        .toList());

        return albumReviewMapper.transform(albumReviewService.createAlbumReview(albumReview));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(produces = "application/json; charset=UTF-8")
    public ResponseEntity<PageResponse<AlbumReviewDTO>> getAlbumReviewsPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "postedDate") String sort,
            @RequestParam(defaultValue = "DESC") String direction) {

        Page<AlbumReview> albumReviewsPage =
                albumReviewService.getAlbumReviewsPaginated(page, size, sort, direction);

        List<AlbumReviewDTO> albumReviewDTOs =
                albumReviewsPage.getContent().stream().map(albumReviewMapper::transform).toList();

        PageResponse<AlbumReviewDTO> response =
                new PageResponse<>(
                        albumReviewDTOs,
                        albumReviewsPage.getNumber(),
                        albumReviewsPage.getSize(),
                        albumReviewsPage.getTotalElements(),
                        albumReviewsPage.getTotalPages(),
                        albumReviewsPage.isLast());

        return ResponseEntity.ok(response);
    }
}
