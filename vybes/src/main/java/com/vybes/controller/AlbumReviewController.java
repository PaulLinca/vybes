package com.vybes.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.vybes.dto.AlbumReviewDTO;
import com.vybes.dto.mapper.PostMapper;
import com.vybes.dto.mapper.TrackReviewMapper;
import com.vybes.dto.request.CreateAlbumReviewRequestDTO;
import com.vybes.external.spotify.SpotifyService;
import com.vybes.external.spotify.model.entity.SpotifyAlbum;
import com.vybes.model.AlbumReview;
import com.vybes.model.TrackReview;
import com.vybes.repository.ArtistRepository;
import com.vybes.repository.UserRepository;
import com.vybes.service.post.PostService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/album-reviews")
@RequiredArgsConstructor
public class AlbumReviewController {

    private final PostService postService;
    private final PostMapper postMapper;
    private final TrackReviewMapper trackReviewMapper;
    private final SpotifyService spotifyService;
    private final UserRepository userRepository;
    private final ArtistRepository artistRepository;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/post", produces = "application/json; charset=UTF-8")
    public AlbumReviewDTO postAlbumReview(@RequestBody CreateAlbumReviewRequestDTO request) throws JsonProcessingException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        AlbumReview albumReview =
                AlbumReview.builder()
                        .spotifyId(request.getSpotifyAlbumId())
                        .score(request.getScore())
                        .build();
        albumReview.setDescription(request.getDescription());
        albumReview.setPostedDate(ZonedDateTime.now());

        albumReview.setComments(new ArrayList<>());
        albumReview.setLikes(new ArrayList<>());
        albumReview.setUser(userRepository.findByEmail(authentication.getName()).orElseThrow());

        SpotifyAlbum spotifyAlbum = spotifyService.getSpotifyAlbum(request.getSpotifyAlbumId());
        albumReview.setSpotifyId(spotifyAlbum.getId());
        albumReview.setAlbumName(spotifyAlbum.getName());
        albumReview.setImageUrl(spotifyAlbum.getImageUrl());
        albumReview.setReleaseDate(spotifyAlbum.getReleaseDate());

        albumReview.setSpotifyArtists(
                spotifyAlbum.getArtists().stream()
                        .map(
                                a ->
                                        Optional.ofNullable(
                                                        artistRepository.findBySpotifyId(a.getId()))
                                                .orElse(
                                                        spotifyService.getArtistAsEntity(
                                                                a.getId())))
                        .collect(Collectors.toCollection(ArrayList::new)));

        List<TrackReview> trackReviews = request.getTrackReviews().stream()
                .map(trackReviewMapper::transform)
                .peek(tr -> tr.setAlbumReview(albumReview))
                .collect(Collectors.toCollection(ArrayList::new));

        albumReview.setTrackReviews(trackReviews);

        AlbumReview savedReview = (AlbumReview) postService.createPost(albumReview);

        return postMapper.transform(savedReview);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{albumReviewId}", produces = "application/json; charset=UTF-8")
    public AlbumReviewDTO getAlbumReview(@PathVariable Long albumReviewId) {
        return postMapper.transform((AlbumReview) postService.getPostById(albumReviewId));
    }
}
