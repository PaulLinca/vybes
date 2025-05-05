package com.vybes.external.spotify.model.search.artist;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.vybes.external.spotify.model.deserializer.SearchArtistResponseDeserializer;
import com.vybes.external.spotify.model.entity.SpotifyArtist;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@Builder
@JsonDeserialize(using = SearchArtistResponseDeserializer.class)
@ToString
public class SearchArtistResponse {
    private List<SpotifyArtist> searchArtistItems;
}
