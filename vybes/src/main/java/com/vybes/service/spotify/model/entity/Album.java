package com.vybes.service.spotify.model.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.vybes.service.spotify.model.SpotifyEntity;
import com.vybes.service.spotify.model.deserializer.AlbumDeserializer;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@JsonDeserialize(using = AlbumDeserializer.class)
public class Album extends SpotifyEntity {
    private Artist artist;
    private String imageUrl;
    private String releaseDate;
}
