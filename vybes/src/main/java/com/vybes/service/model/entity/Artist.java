package com.vybes.service.model.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.vybes.service.model.SpotifyEntity;
import com.vybes.service.model.deserializer.ArtistDeserializer;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@SuperBuilder
@JsonDeserialize(using = ArtistDeserializer.class)
public class Artist extends SpotifyEntity {
    private String spotifyUrl;
    private Integer popularity;
    private String imageUrl;
    private List<String> genres;
}
