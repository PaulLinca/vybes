package com.vybes.controller;

import com.vybes.service.spotify.SpotifyService;
import com.vybes.service.spotify.model.entity.Album;
import com.vybes.service.spotify.model.entity.Artist;
import com.vybes.service.spotify.model.entity.Track;
import com.vybes.service.spotify.model.search.SearchTrackItem;

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
    @GetMapping(value = "/search", produces = "application/json; charset=UTF-8")
    public List<SearchTrackItem> search(@RequestParam String query) {
        return spotifyService.searchTrack(query);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/track", produces = "application/json; charset=UTF-8")
    public Track getTrack(@RequestParam String id) {
        return spotifyService.getTrack(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/album", produces = "application/json; charset=UTF-8")
    public Album getAlbum(@RequestParam String id) {
        return spotifyService.getAlbum(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/artist", produces = "application/json; charset=UTF-8")
    public Artist getArtist(@RequestParam String id) {
        return spotifyService.getArtist(id);
    }
}
