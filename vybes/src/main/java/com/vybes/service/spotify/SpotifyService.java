package com.vybes.service.spotify;

import com.vybes.dto.AlbumDTO;
import com.vybes.dto.ArtistDTO;
import com.vybes.service.spotify.client.SpotifyClient;
import com.vybes.service.spotify.model.entity.SpotifyAlbum;
import com.vybes.service.spotify.model.entity.SpotifyArtist;
import com.vybes.service.spotify.model.entity.SpotifyTrack;
import com.vybes.service.spotify.model.search.track.SearchTrackItem;

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

    public List<ArtistDTO> searchArtist(String searchQuery) {
        try {
            return spotifyClient.searchArtist(searchQuery).stream()
                    .map(
                            a ->
                                    ArtistDTO.builder()
                                            .spotifyId(a.getId())
                                            .name(a.getName())
                                            .imageUrl(a.getImageUrl())
                                            .build())
                    .toList();
        } catch (HttpClientErrorException.Unauthorized e) {
            spotifyClient.refreshAccessToken();
            return searchArtist(searchQuery);
        }
    }

    public List<AlbumDTO> searchAlbum(String searchQuery) {
        try {
            return spotifyClient.searchAlbum(searchQuery).stream()
                    .map(
                            a ->
                                    AlbumDTO.builder()
                                            .spotifyId(a.getId())
                                            .name(a.getName())
                                            .imageUrl(a.getImageUrl())
                                            .build())
                    .toList();
        } catch (HttpClientErrorException.Unauthorized e) {
            spotifyClient.refreshAccessToken();
            return searchAlbum(searchQuery);
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
