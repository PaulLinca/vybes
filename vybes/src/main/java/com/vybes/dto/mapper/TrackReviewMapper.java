package com.vybes.dto.mapper;

import com.vybes.dto.TrackReviewDTO;
import com.vybes.model.TrackReview;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TrackReviewMapper {
    @Mapping(target = "isFavorite", source = "favorite")
    @Mapping(target = "name", source = "track.name")
    @Mapping(target = "spotifyTrackId", source = "track.externalId")
    TrackReviewDTO transformToDTO(TrackReview trackReview);
}
