package com.vybes.dto.mapper;

import com.vybes.dto.AlbumDTO;
import com.vybes.service.user.model.Album;
import com.vybes.service.vybe.entity.Artist;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper
public interface AlbumMapper {
    @Mapping(source = "album.artist", target = "artist", qualifiedByName = "mapArtist")
    AlbumDTO transform(Album album);

    @Named("mapArtist")
    default String mapArtist(Artist artist) {
        return artist != null ? artist.getName() : null;
    }
}
