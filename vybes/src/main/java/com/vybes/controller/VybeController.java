package com.vybes.controller;

import com.vybes.dto.VybeDTO;
import com.vybes.dto.mapper.PostMapper;
import com.vybes.dto.request.CreateVybeRequestDTO;
import com.vybes.external.spotify.SpotifyService;
import com.vybes.external.spotify.model.entity.SpotifyTrack;
import com.vybes.model.Artist;
import com.vybes.model.Vybe;
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
import java.util.Optional;

@RestController
@RequestMapping("/api/vybes")
@RequiredArgsConstructor
public class VybeController {

    private final SpotifyService spotifyService;
    private final ArtistRepository artistRepository;
    private final UserRepository userRepository;
    private final PostService postService;
    private final PostMapper postMapper;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/post", produces = "application/json; charset=UTF-8")
    public VybeDTO postVybe(@RequestBody CreateVybeRequestDTO request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Vybe vybe = new Vybe();
        vybe.setDescription(request.getDescription());
        vybe.setSpotifyId(request.getSpotifyTrackId());
        vybe.setPostedDate(ZonedDateTime.now());

        vybe.setComments(new ArrayList<>());
        vybe.setLikes(new ArrayList<>());
        vybe.setUser(userRepository.findByEmail(authentication.getName()).orElseThrow());

        SpotifyTrack spotifyTrack = spotifyService.getTrack(request.getSpotifyTrackId());
        vybe.setSpotifyId(spotifyTrack.getId());
        vybe.setSongName(spotifyTrack.getName());
        vybe.setSpotifyAlbumId(spotifyTrack.getAlbum().getId());
        vybe.setImageUrl(spotifyTrack.getAlbum().getImageUrl());
        vybe.setSpotifyArtists(
                spotifyTrack.getArtists().stream()
                        .map(
                                a ->
                                        Optional.ofNullable(
                                                        artistRepository.findBySpotifyId(a.getId()))
                                                .orElseGet(
                                                        () ->
                                                                artistRepository.save(
                                                                        Artist.builder()
                                                                                .spotifyId(
                                                                                        a.getId())
                                                                                .name(a.getName())
                                                                                .build())))
                        .toList());

        return postMapper.transform((Vybe) postService.createPost(vybe));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{vybeId}", produces = "application/json; charset=UTF-8")
    public VybeDTO getVybe(@PathVariable Long vybeId) {
        return postMapper.transform((Vybe) postService.getPostById(vybeId));
    }
}
