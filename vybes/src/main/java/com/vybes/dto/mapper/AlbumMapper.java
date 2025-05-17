package com.vybes.dto.mapper;

import com.vybes.dto.AlbumDTO;
import com.vybes.dto.TrackDTO;
import com.vybes.external.spotify.model.entity.SpotifyAlbum;
import com.vybes.external.spotify.model.entity.SpotifyArtist;
import com.vybes.external.spotify.model.entity.SpotifyEntity;
import com.vybes.external.spotify.model.entity.SpotifyTrack;
import com.vybes.model.Album;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(
        componentModel = "spring",
        uses = {ArtistMapper.class})
public interface AlbumMapper {
    AlbumDTO transform(Album album);

    @Mapping(target = "spotifyId", source = "id")
    AlbumDTO transform(SpotifyAlbum album);

    @Mapping(target = "spotifyId", source = "id")
    @Mapping(target = "artists", source = "artists")
    TrackDTO transform(SpotifyTrack track);

    default String getArtistName(SpotifyArtist artist) {
        return artist.getName();
    }
}
