package com.vybes.service.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.vybes.service.model.deserializer.ArtistDeserializer;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(using = ArtistDeserializer.class)
public class Artist {
    private String id;
    private String name;
    private String spotifyUrl;
    private Integer popularity;
    private String imageUrl;
    private List<String> genres;
}
