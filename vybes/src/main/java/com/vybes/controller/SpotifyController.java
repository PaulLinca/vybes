package com.vybes.controller;

import com.vybes.service.client.SpotifyService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class SpotifyController {

    @Autowired private SpotifyService spotifyService;

    @GetMapping("/search")
    public Object search(@RequestParam String query) {
        return spotifyService.searchTrack(query);
    }
}
