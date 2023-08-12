package com.vybes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.vybes.service.client.SpotifyClient;

@RestController
@RequestMapping("/api")
public class SpotifyController {

    @Autowired private SpotifyClient spotifyClient;

    @GetMapping("/track")
    public String getTrack() {
        return "track";
    }

    @GetMapping("/search")
    public Object search() {
        return spotifyClient.searchTrack();
    }
}
