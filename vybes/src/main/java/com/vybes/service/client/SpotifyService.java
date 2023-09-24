package com.vybes.service.client;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

@Component
@RequiredArgsConstructor
public class SpotifyService {

    private final SpotifyClient spotifyClient;

    public Object searchTrack(String searchQuery) {
        try {
            return spotifyClient.searchTrack(searchQuery);
        } catch (HttpClientErrorException.Unauthorized e) {
            spotifyClient.setAccessToken();
            return searchTrack(searchQuery);
        }
    }
}
