package com.vybes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.vybes.service.client.SpotifyService;

@RestController
@RequestMapping("/api")
public class SpotifyController {

    @Autowired
    private SpotifyService spotifyService;

    @GetMapping("/search")
    public Object search() {
        return spotifyService.searchTrack();
    }
}
