package com.vybes.service.music;

import com.vybes.dto.AlbumDTO;
import com.vybes.dto.ArtistDTO;
import com.vybes.dto.TrackDTO;
import com.vybes.model.Album;
import com.vybes.model.Artist;
import com.vybes.model.Track;

import java.util.List;

public interface MusicService {

    // Search operations (return DTOs, don't persist)
    List<TrackDTO> searchTrack(String searchQuery);

    List<ArtistDTO> searchArtist(String searchQuery);

    List<AlbumDTO> searchAlbum(String searchQuery);

    // Get or create operations (persist and return entities)
    Track getOrCreateTrack(String externalId);

    Album getOrCreateAlbum(String externalId);

    Artist getOrCreateArtist(String externalId);

    // Fetch operations (return DTOs with fresh data)
    TrackDTO getTrackDetails(String externalId);

    AlbumDTO getAlbumDetails(String externalId);

    ArtistDTO getArtistDetails(String externalId);
}
