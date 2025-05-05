package com.vybes.service.spotify.model.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.vybes.service.spotify.model.deserializer.ArtistDeserializer;

import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@SuperBuilder
@JsonDeserialize(using = ArtistDeserializer.class)
@ToString
public class SpotifyArtist extends SpotifyEntity {
    private String spotifyUrl;
    private Integer popularity;
    private String imageUrl;
    private List<String> genres;
}
