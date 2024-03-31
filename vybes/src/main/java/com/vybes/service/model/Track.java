package com.vybes.service.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.vybes.service.model.deserializer.TrackDeserializer;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(using = TrackDeserializer.class)
public class Track {
    private String id;
    private String name;
    private Album album;
    private List<Artist> artist;
    private String spotifyUrl;
}
