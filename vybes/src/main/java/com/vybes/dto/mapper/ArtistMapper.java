package com.vybes.dto.mapper;

import com.vybes.dto.ArtistDTO;
import com.vybes.external.spotify.model.entity.SpotifyArtist;
import com.vybes.model.Artist;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ArtistMapper {

    @Mapping(target = "spotifyId", source = "externalId")
    ArtistDTO transformToDTO(Artist artist);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "externalId", source = "spotifyId")
    @Mapping(target = "provider", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "albums", ignore = true)
    @Mapping(target = "favoritedBy", ignore = true)
    Artist transformToEntity(ArtistDTO artist);

    @Mapping(target = "spotifyId", source = "id")
    ArtistDTO transformToDTO(SpotifyArtist artist);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "externalId", source = "id")
    @Mapping(target = "provider", constant = "SPOTIFY")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "albums", ignore = true)
    @Mapping(target = "favoritedBy", ignore = true)
    Artist transformToEntity(SpotifyArtist artist);
}
