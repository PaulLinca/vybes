package com.vybes.dto.request;

import java.util.List;
import com.vybes.dto.TrackReviewDTO;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateAlbumReviewRequestDTO {
    private String spotifyAlbumId;
    private Integer score;
    private String description;
    private List<TrackReviewDTO> trackReviews;
}
