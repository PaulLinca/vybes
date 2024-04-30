package com.vybes.service.writer.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import lombok.Builder;
import lombok.Data;

import java.time.ZonedDateTime;
import java.util.List;

@Data
@Builder
public class Vybe {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String songName;

    private String spotifyTrackId;

    private List<String> spotifyArtistIds;

    private String spotifyAlbumId;

    private String imageUrl;

    private ZonedDateTime postedDate;
}
