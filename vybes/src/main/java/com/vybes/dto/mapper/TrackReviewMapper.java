package com.vybes.dto.mapper;

import com.vybes.dto.TrackReviewDTO;
import com.vybes.model.TrackReview;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TrackReviewMapper {
    TrackReviewDTO transform(TrackReview trackReview);

    TrackReview transform(TrackReviewDTO trackReview);
}
