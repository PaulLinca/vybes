package com.vybes.controller;

import com.vybes.service.client.SpotifyService;
import com.vybes.service.model.search.SearchTrackItem;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class SpotifyController {

    private final SpotifyService spotifyService;

    @SneakyThrows
    @GetMapping(value = "/search", produces = "application/json; charset=UTF-8")
    public List<SearchTrackItem> search(@RequestParam String query) {
        return spotifyService.searchTrack(query);
    }
}
