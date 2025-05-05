package com.vybes.external.spotify.model.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.vybes.external.spotify.model.deserializer.AlbumDeserializer;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@SuperBuilder
@JsonDeserialize(using = AlbumDeserializer.class)
@NoArgsConstructor
@ToString(callSuper = true)
public class SpotifyAlbum extends SpotifyEntity {
    private List<SpotifyArtist> artists;
    private String imageUrl;
    private String releaseDate;
}
