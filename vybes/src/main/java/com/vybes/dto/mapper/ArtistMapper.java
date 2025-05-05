package com.vybes.dto.mapper;

import com.vybes.dto.ArtistDTO;
import com.vybes.model.Artist;

import org.mapstruct.Mapper;

@Mapper
public interface ArtistMapper {
    ArtistDTO transform(Artist artist);

    Artist transform(ArtistDTO artist);
}
