package com.vybes.controller;

import com.vybes.dto.AlbumDTO;
import com.vybes.dto.ArtistDTO;
import com.vybes.service.spotify.SpotifyService;
import com.vybes.service.spotify.model.entity.SpotifyAlbum;
import com.vybes.service.spotify.model.entity.SpotifyArtist;
import com.vybes.service.spotify.model.entity.SpotifyTrack;
import com.vybes.service.spotify.model.search.track.SearchTrackItem;

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

    private final SpotifyService spotifyService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/search/track", produces = "application/json; charset=UTF-8")
    public List<SearchTrackItem> searchTrack(@RequestParam String query) {
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
    public SpotifyTrack getTrack(@RequestParam String id) {
        return spotifyService.getTrack(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/album", produces = "application/json; charset=UTF-8")
    public SpotifyAlbum getAlbum(@RequestParam String id) {
        return spotifyService.getAlbum(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/artist", produces = "application/json; charset=UTF-8")
    public SpotifyArtist getArtist(@RequestParam String id) {
        return spotifyService.getArtist(id);
    }
}
