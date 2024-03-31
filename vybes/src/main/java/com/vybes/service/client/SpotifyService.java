package com.vybes.service.client;

import com.vybes.service.model.entity.Album;
import com.vybes.service.model.entity.Artist;
import com.vybes.service.model.entity.Track;
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
            spotifyClient.refreshAccessToken();
            return searchTrack(searchQuery);
        }
    }

    public Track getTrack(String id) {
        try {
            return spotifyClient.getTrack(id);
        } catch (HttpClientErrorException.Unauthorized e) {
            spotifyClient.refreshAccessToken();
            return getTrack(id);
        }
    }

    public Album getAlbum(String id) {
        try {
            return spotifyClient.getAlbum(id);
        } catch (HttpClientErrorException.Unauthorized e) {
            spotifyClient.refreshAccessToken();
            return getAlbum(id);
        }
    }

    public Artist getArtist(String id) {
        try {
            return spotifyClient.getArtist(id);
        } catch (HttpClientErrorException.Unauthorized e) {
            spotifyClient.refreshAccessToken();
            return getArtist(id);
        }
    }
}
