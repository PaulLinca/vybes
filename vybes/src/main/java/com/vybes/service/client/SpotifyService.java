package com.vybes.service.client;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

@Component
@RequiredArgsConstructor
public class SpotifyService {

    private final SpotifyClient spotifyClient;

    public Object searchTrack() {
        try {
            return spotifyClient.searchTrack();
        } catch (HttpClientErrorException.Unauthorized e) {
            spotifyClient.setAccessToken();
            return searchTrack();
        }
    }
}
