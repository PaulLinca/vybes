package com.vybes.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vybes.model.TrackReview;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrackReviewDTO {
    private Long id;
    private String name;
    private String spotifyTrackId;
    private TrackReview.TrackRating rating;

    @JsonProperty("isFavorite")
    private boolean isFavorite;
}
