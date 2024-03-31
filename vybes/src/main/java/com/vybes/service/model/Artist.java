package com.vybes.service.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.vybes.service.model.deserializer.ArtistDeserializer;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(using = ArtistDeserializer.class)
public class Artist {
    private String id;
    private String name;
    private String spotifyUrl;
}
