package com.vybes.service.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.vybes.service.model.deserializer.AlbumDeserializer;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(using = AlbumDeserializer.class)
public class Album {
    private String id;
    private String name;
    private Artist artist;
    private String imageUrl;
    private String spotifyUrl;
    private String releaseDate;
}
