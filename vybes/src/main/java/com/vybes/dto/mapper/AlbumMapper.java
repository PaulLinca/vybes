package com.vybes.dto.mapper;

import com.vybes.dto.AlbumDTO;
import com.vybes.external.spotify.model.entity.SpotifyAlbum;
import com.vybes.model.Album;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        componentModel = "spring",
        uses = {ArtistMapper.class})
public interface AlbumMapper {
    AlbumDTO transform(Album album);

    @Mapping(target = "spotifyId", source = "id")
    AlbumDTO transform(SpotifyAlbum album);
}
