package com.vybes.dto.mapper;

import com.vybes.dto.ArtistDTO;
import com.vybes.service.vybe.entity.Artist;

import org.mapstruct.Mapper;

@Mapper
public interface ArtistMapper {
    ArtistDTO transform(Artist artist);
}
