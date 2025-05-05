package com.vybes.dto.mapper;

import com.vybes.dto.AlbumReviewDTO;
import com.vybes.dto.request.CreateAlbumReviewRequestDTO;
import com.vybes.model.AlbumReview;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        componentModel = "spring",
        uses = {ArtistMapper.class})
public interface AlbumReviewMapper {

    @Mapping(target = "postedDate", expression = "java(java.time.ZonedDateTime.now())")
    AlbumReview transform(CreateAlbumReviewRequestDTO dto);

    @Mapping(source = "spotifyArtists", target = "artists")
    AlbumReviewDTO transform(AlbumReview albumReview);
}
