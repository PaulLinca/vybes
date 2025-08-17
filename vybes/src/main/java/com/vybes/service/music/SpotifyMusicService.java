package com.vybes.service.music;

import com.vybes.dto.AlbumDTO;
import com.vybes.dto.ArtistDTO;
import com.vybes.dto.TrackDTO;
import com.vybes.dto.mapper.AlbumMapper;
import com.vybes.dto.mapper.ArtistMapper;
import com.vybes.dto.mapper.TrackMapper;
import com.vybes.external.spotify.SpotifyDataClient;
import com.vybes.external.spotify.model.entity.SpotifyAlbum;
import com.vybes.external.spotify.model.entity.SpotifyArtist;
import com.vybes.external.spotify.model.entity.SpotifyTrack;
import com.vybes.model.Album;
import com.vybes.model.Artist;
import com.vybes.model.MusicProvider;
import com.vybes.model.Track;
import com.vybes.repository.AlbumRepository;
import com.vybes.repository.ArtistRepository;
import com.vybes.repository.TrackRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SpotifyMusicService implements MusicService {

    private final SpotifyDataClient spotifyDataClient;

    private final AlbumRepository albumRepository;
    private final ArtistRepository artistRepository;
    private final TrackRepository trackRepository;

    private final AlbumMapper albumMapper;
    private final ArtistMapper artistMapper;
    private final TrackMapper trackMapper;

    @Override
    public List<TrackDTO> searchTrack(String searchQuery) {
        return spotifyDataClient.searchTracks(searchQuery).stream()
                .map(trackMapper::transformToDTO)
                .toList();
    }

    @Override
    public List<ArtistDTO> searchArtist(String searchQuery) {
        return spotifyDataClient.searchArtists(searchQuery).stream()
                .map(artistMapper::transformToDTO)
                .toList();
    }

    @Override
    public List<AlbumDTO> searchAlbum(String searchQuery) {
        return spotifyDataClient.searchAlbums(searchQuery).stream()
                .map(albumMapper::transformToDTO)
                .toList();
    }

    @Override
    public Track getOrCreateTrack(String spotifyId) {
        return trackRepository
                .findByExternalIdAndProvider(spotifyId, MusicProvider.SPOTIFY)
                .orElseGet(
                        () -> {
                            SpotifyTrack spotifyTrack = spotifyDataClient.getTrack(spotifyId);
                            return createAndSaveTrack(spotifyTrack);
                        });
    }

    @Override
    public Album getOrCreateAlbum(String spotifyId) {
        return albumRepository
                .findByExternalIdAndProvider(spotifyId, MusicProvider.SPOTIFY)
                .orElseGet(
                        () -> {
                            SpotifyAlbum spotifyAlbum = spotifyDataClient.getAlbum(spotifyId);
                            return createAndSaveAlbum(spotifyAlbum);
                        });
    }

    @Override
    public Artist getOrCreateArtist(String spotifyId) {
        return artistRepository
                .findByExternalIdAndProvider(spotifyId, MusicProvider.SPOTIFY)
                .orElseGet(
                        () -> {
                            SpotifyArtist spotifyArtist = spotifyDataClient.getArtist(spotifyId);
                            return createAndSaveArtist(spotifyArtist);
                        });
    }

    @Override
    public TrackDTO getTrackDetails(String spotifyId) {
        SpotifyTrack spotifyTrack = spotifyDataClient.getTrack(spotifyId);
        return trackMapper.transformToDTO(spotifyTrack);
    }

    @Override
    public AlbumDTO getAlbumDetails(String spotifyId) {
        SpotifyAlbum spotifyAlbum = spotifyDataClient.getAlbum(spotifyId);
        return albumMapper.transformToDTO(spotifyAlbum);
    }

    @Override
    public ArtistDTO getArtistDetails(String spotifyId) {
        SpotifyArtist spotifyArtist = spotifyDataClient.getArtist(spotifyId);
        return artistMapper.transformToDTO(spotifyArtist);
    }

    private Track createAndSaveTrack(SpotifyTrack spotifyTrack) {
        Set<Artist> artists = spotifyTrack.getArtists().stream()
                .map(spotifyArtist -> getOrCreateArtist(spotifyArtist.getId()))
                .collect(Collectors.toSet());

        Album album = getOrCreateAlbum(spotifyTrack.getAlbum().getId());

        Track track = trackMapper.transformToEntity(spotifyTrack);
        track.setAlbum(album);
        track.setArtists(artists);

        return trackRepository.save(track);
    }

    private Album createAndSaveAlbum(SpotifyAlbum spotifyAlbum) {
        Set<Artist> artists = spotifyAlbum.getArtists().stream()
                .map(spotifyArtist -> getOrCreateArtist(spotifyArtist.getId()))
                .collect(Collectors.toSet());

        Album album = albumMapper.transformToEntity(spotifyAlbum);
        album.setArtists(artists);

        return albumRepository.save(album);
    }

    private Artist createAndSaveArtist(SpotifyArtist spotifyArtist) {
        Artist artist = artistMapper.transformToEntity(spotifyArtist);
        return artistRepository.save(artist);
    }
}
