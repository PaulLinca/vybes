package com.vybes.external.spotify;

import com.vybes.dto.AlbumDTO;
import com.vybes.dto.ArtistDTO;
import com.vybes.dto.mapper.AlbumMapper;
import com.vybes.dto.mapper.ArtistMapper;
import com.vybes.external.spotify.model.entity.SpotifyAlbum;
import com.vybes.external.spotify.model.entity.SpotifyArtist;
import com.vybes.external.spotify.model.entity.SpotifyTrack;
import com.vybes.model.Album;
import com.vybes.model.Artist;
import com.vybes.model.Post;
import com.vybes.model.VybesUser;
import com.vybes.repository.PostRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

// TODO: Make this be an implementation of a MusicService and use DI to inject SpotifyService (avoid
// using Spotify specific models)
@Component
@RequiredArgsConstructor
public class SpotifyService {

    private final SpotifyClient spotifyClient;
    private final AlbumMapper albumMapper;
    private final ArtistMapper artistMapper;
    private final PostRepository postRepository;

    public List<SpotifyTrack> searchTrack(String searchQuery) {
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
                    .map(artistMapper::transform)
                    .toList();
        } catch (HttpClientErrorException.Unauthorized e) {
            spotifyClient.refreshAccessToken();
            return searchArtist(searchQuery);
        }
    }

    public List<AlbumDTO> searchAlbum(String searchQuery) {
        try {
            return spotifyClient.searchAlbum(searchQuery).stream()
                    .map(albumMapper::transform)
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

    public AlbumDTO getAlbum(String id, boolean findReview, VybesUser userId) {
        try {
            var albumDTO = albumMapper.transform(spotifyClient.getAlbum(id));
            if (findReview) {
                albumDTO.setReviewId(
                        postRepository
                                .findAlbumReviewBySpotifyIdAndUser(id, userId)
                                .map(Post::getId)
                                .orElse(null));
            }
            return albumDTO;
        } catch (HttpClientErrorException.Unauthorized e) {
            spotifyClient.refreshAccessToken();
            return getAlbum(id, findReview, userId);
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

    public SpotifyAlbum getSpotifyAlbum(String id) {
        try {
            return spotifyClient.getAlbum(id);
        } catch (HttpClientErrorException.Unauthorized e) {
            spotifyClient.refreshAccessToken();
            return getSpotifyAlbum(id);
        }
    }

    public Artist getArtistAsEntity(String id) {
        try {
            SpotifyArtist spotifyArtist = spotifyClient.getArtist(id);
            return Artist.builder()
                    .spotifyId(spotifyArtist.getId())
                    .name(spotifyArtist.getName())
                    .imageUrl(spotifyArtist.getImageUrl())
                    .build();
        } catch (HttpClientErrorException.Unauthorized e) {
            spotifyClient.refreshAccessToken();
            return getArtistAsEntity(id);
        }
    }
}
