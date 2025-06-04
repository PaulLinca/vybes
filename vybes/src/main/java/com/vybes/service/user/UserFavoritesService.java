package com.vybes.service.user;

import com.vybes.dto.request.FavoritesUpdateRequest;
import com.vybes.exception.InvalidRequestException;
import com.vybes.external.spotify.SpotifyService;
import com.vybes.external.spotify.model.entity.SpotifyAlbum;
import com.vybes.external.spotify.model.entity.SpotifyArtist;
import com.vybes.model.Album;
import com.vybes.model.Artist;
import com.vybes.model.VybesUser;
import com.vybes.repository.AlbumRepository;
import com.vybes.repository.ArtistRepository;

import jakarta.transaction.Transactional;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserFavoritesService {

    private final ArtistRepository artistRepository;
    private final AlbumRepository albumRepository;
    private final SpotifyService spotifyService;

    @Transactional
    public void updateUserFavorites(VybesUser user, FavoritesUpdateRequest request) {

        if (request.getArtistIds() != null && request.getArtistIds().size() > 3) {
            throw new InvalidRequestException("You can only have up to 3 favorite artists");
        }

        if (request.getAlbumIds() != null && request.getAlbumIds().size() > 3) {
            throw new InvalidRequestException("You can only have up to 3 favorite albums");
        }

        if (request.getArtistIds() != null) {
            Set<Artist> artists = new HashSet<>();

            for (String spotifyId : request.getArtistIds()) {
                Artist artist = artistRepository.findBySpotifyId(spotifyId);

                if (artist == null) {
                    SpotifyArtist spotifyArtist = spotifyService.getArtist(spotifyId);
                    artist =
                            Artist.builder()
                                    .spotifyId(spotifyId)
                                    .name(spotifyArtist.getName())
                                    .imageUrl(spotifyArtist.getImageUrl())
                                    .vybes(new ArrayList<>())
                                    .favoritedBy(new HashSet<>())
                                    .build();

                    artist = artistRepository.save(artist);
                }

                artists.add(artist);
            }

            user.getFavoriteArtists().clear();
            user.getFavoriteArtists().addAll(artists);
        }

        if (request.getAlbumIds() != null) {
            Set<Album> albums = new HashSet<>();

            for (String spotifyId : request.getAlbumIds()) {
                Album album = albumRepository.findBySpotifyId(spotifyId);

                if (album == null) {
                    SpotifyAlbum spotifyAlbum = spotifyService.getSpotifyAlbum(spotifyId);
                    album =
                            Album.builder()
                                    .spotifyId(spotifyId)
                                    .name(spotifyAlbum.getName())
                                    .imageUrl(spotifyAlbum.getImageUrl())
                                    .fans(new HashSet<>())
                                    .build();

                    album = albumRepository.save(album);
                }

                albums.add(album);
            }

            user.getFavoriteAlbums().clear();
            user.getFavoriteAlbums().addAll(albums);
        }
    }
}
