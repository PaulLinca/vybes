package com.vybes.controller;

import com.vybes.dto.AlbumDTO;
import com.vybes.dto.ArtistDTO;
import com.vybes.dto.TrackDTO;
import com.vybes.repository.UserRepository;
import com.vybes.service.music.SpotifyMusicService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/music")
@RequiredArgsConstructor
public class SpotifyController {

    private final SpotifyMusicService spotifyService;
    private final UserRepository userRepository;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/search/track", produces = "application/json; charset=UTF-8")
    public List<TrackDTO> searchTrack(@RequestParam String query) {
        return spotifyService.searchTrack(query);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/search/artist", produces = "application/json; charset=UTF-8")
    public List<ArtistDTO> searchArtist(@RequestParam String query) {
        return spotifyService.searchArtist(query);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/search/album", produces = "application/json; charset=UTF-8")
    public List<AlbumDTO> searchAlbum(@RequestParam String query) {
        return spotifyService.searchAlbum(query);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/track", produces = "application/json; charset=UTF-8")
    public TrackDTO getTrack(@RequestParam String id) {
        return spotifyService.getTrackDetails(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/album", produces = "application/json; charset=UTF-8")
    public AlbumDTO getAlbum(@RequestParam String id, @RequestParam boolean findReview) {
        return spotifyService.getAlbumDetails(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/artist", produces = "application/json; charset=UTF-8")
    public ArtistDTO getArtist(@RequestParam String id) {
        return spotifyService.getArtistDetails(id);
    }
}
