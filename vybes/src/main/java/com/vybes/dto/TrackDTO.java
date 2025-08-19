package com.vybes.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@Builder
@ToString
public class TrackDTO {
    private String spotifyId;
    private String name;
    private List<String> artists;
    private String imageUrl;
}
