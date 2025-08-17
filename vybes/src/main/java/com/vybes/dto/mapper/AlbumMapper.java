package com.vybes.dto.mapper;

import com.vybes.dto.AlbumDTO;
import com.vybes.external.spotify.model.entity.SpotifyAlbum;
import com.vybes.model.Album;

import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        uses = {ArtistMapper.class, TrackMapper.class})
public interface AlbumMapper {

    @Mapping(target = "spotifyId", source = "externalId")
    @Mapping(target = "reviewId", ignore = true)
    AlbumDTO transformToDTO(Album album);

    @Mapping(target = "spotifyId", source = "id")
    @Mapping(target = "reviewId", ignore = true)
    AlbumDTO transformToDTO(SpotifyAlbum album);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "externalId", source = "id")
    @Mapping(target = "provider", constant = "SPOTIFY")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "fans", ignore = true)
    Album transformToEntity(SpotifyAlbum spotifyAlbum);
}
