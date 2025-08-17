package com.vybes.dto.mapper;

import com.vybes.dto.TrackDTO;
import com.vybes.external.spotify.model.entity.SpotifyArtist;
import com.vybes.external.spotify.model.entity.SpotifyTrack;
import com.vybes.model.Artist;
import com.vybes.model.Track;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Collections;
import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface TrackMapper {
    @Mapping(target = "spotifyId", source = "externalId")
    @Mapping(target = "artists", source = "artists", qualifiedByName = "artistsToNames")
    TrackDTO transformToDTO(Track track);

    @Mapping(target = "spotifyId", source = "id")
    @Mapping(target = "artists", source = "artists", qualifiedByName = "spotifyArtistsToNames")
    TrackDTO transformToDTO(SpotifyTrack track);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "externalId", source = "id")
    @Mapping(target = "provider", constant = "SPOTIFY")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "album", ignore = true)
    @Mapping(target = "artists", ignore = true)
    Track transformToEntity(SpotifyTrack spotifyTrack);

    @Named("artistsToNames")
    default List<String> artistsToNames(Set<Artist> artists) {
        return artists != null
                ? artists.stream().map(Artist::getName).toList()
                : Collections.emptyList();
    }

    @Named("spotifyArtistsToNames")
    default List<String> spotifyArtistsToNames(List<SpotifyArtist> artists) {
        return artists != null
                ? artists.stream().map(SpotifyArtist::getName).toList()
                : Collections.emptyList();
    }
}
