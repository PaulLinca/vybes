package com.vybes.dto.request;

import com.vybes.dto.TrackReviewDTO;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CreateAlbumReviewRequestDTO {
    private String spotifyAlbumId;
    private Integer score;
    private String description;
    private List<TrackReviewDTO> trackReviews;
}
