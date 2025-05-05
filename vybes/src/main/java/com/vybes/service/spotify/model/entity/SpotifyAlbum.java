package com.vybes.service.spotify.model.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.vybes.service.spotify.model.deserializer.AlbumDeserializer;

import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@SuperBuilder
@JsonDeserialize(using = AlbumDeserializer.class)
@ToString(callSuper = true)
public class SpotifyAlbum extends SpotifyEntity {
    private List<SpotifyArtist> artists;
    private String imageUrl;
    private String releaseDate;
}
