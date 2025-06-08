package com.vybes.dto.mapper;

import com.vybes.dto.TrackReviewDTO;
import com.vybes.model.TrackReview;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TrackReviewMapper {
    @Mapping(target = "isFavorite", source = "favorite")
    TrackReviewDTO transform(TrackReview trackReview);

    @Mapping(target = "isFavorite", source = "favorite")
    TrackReview transform(TrackReviewDTO trackReview);
}
