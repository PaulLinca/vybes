package com.vybes.dto.mapper;

import com.vybes.dto.ArtistDTO;
import com.vybes.external.spotify.model.entity.SpotifyArtist;
import com.vybes.model.Artist;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface ArtistMapper {
    ArtistDTO transform(Artist artist);

    Artist transform(ArtistDTO artist);

    @Mapping(target = "spotifyId", source = "id")
    ArtistDTO transform(SpotifyArtist artist);
}
