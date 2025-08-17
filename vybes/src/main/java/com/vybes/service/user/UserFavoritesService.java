package com.vybes.service.user;

import com.vybes.dto.request.FavoritesUpdateRequest;
import com.vybes.exception.InvalidRequestException;
import com.vybes.model.Album;
import com.vybes.model.Artist;
import com.vybes.model.VybesUser;
import com.vybes.service.music.SpotifyMusicService;

import jakarta.transaction.Transactional;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserFavoritesService {

    private final SpotifyMusicService spotifyService;

    @Transactional
    public void updateUserFavorites(VybesUser user, FavoritesUpdateRequest request) {
        validateRequest(request);

        Set<Artist> artists =
                Optional.of(request.getArtistIds()).orElse(Collections.emptyList()).stream()
                        .map(spotifyService::getOrCreateArtist)
                        .collect(Collectors.toSet());

        user.getFavoriteArtists().clear();
        user.getFavoriteArtists().addAll(artists);

        Set<Album> albums =
                Optional.of(request.getAlbumIds()).orElse(Collections.emptyList()).stream()
                        .map(spotifyService::getOrCreateAlbum)
                        .collect(Collectors.toSet());

        user.getFavoriteAlbums().clear();
        user.getFavoriteAlbums().addAll(albums);
    }

    private void validateRequest(FavoritesUpdateRequest request) {
        if (request.getArtistIds() != null && request.getArtistIds().size() > 3) {
            throw new InvalidRequestException("You can only have up to 3 favorite artists");
        }

        if (request.getAlbumIds() != null && request.getAlbumIds().size() > 3) {
            throw new InvalidRequestException("You can only have up to 3 favorite albums");
        }
    }
}
