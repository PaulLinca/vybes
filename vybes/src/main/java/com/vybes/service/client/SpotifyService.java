package com.vybes.service.client;

import com.vybes.service.model.search.SearchTrackItem;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SpotifyService {

    private final SpotifyClient spotifyClient;

    public List<SearchTrackItem> searchTrack(String searchQuery) {
        try {
            return spotifyClient.searchTrack(searchQuery);
        } catch (HttpClientErrorException.Unauthorized e) {
            spotifyClient.setAccessToken();
            return searchTrack(searchQuery);
        }
    }
}
