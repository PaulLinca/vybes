package com.vybes.service.spotify;

import com.vybes.service.spotify.client.SpotifyClient;
import com.vybes.service.spotify.model.entity.SpotifyAlbum;
import com.vybes.service.spotify.model.entity.SpotifyArtist;
import com.vybes.service.spotify.model.entity.SpotifyTrack;
import com.vybes.service.spotify.model.search.SearchTrackItem;

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
            spotifyClient.refreshAccessToken();
            return searchTrack(searchQuery);
        }
    }

    public SpotifyTrack getTrack(String id) {
        try {
            return spotifyClient.getTrack(id);
        } catch (HttpClientErrorException.Unauthorized e) {
            spotifyClient.refreshAccessToken();
            return getTrack(id);
        }
    }

    public SpotifyAlbum getAlbum(String id) {
        try {
            return spotifyClient.getAlbum(id);
        } catch (HttpClientErrorException.Unauthorized e) {
            spotifyClient.refreshAccessToken();
            return getAlbum(id);
        }
    }

    public SpotifyArtist getArtist(String id) {
        try {
            return spotifyClient.getArtist(id);
        } catch (HttpClientErrorException.Unauthorized e) {
            spotifyClient.refreshAccessToken();
            return getArtist(id);
        }
    }
}
