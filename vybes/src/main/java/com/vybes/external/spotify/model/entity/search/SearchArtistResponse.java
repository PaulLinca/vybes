package com.vybes.external.spotify.model.entity.search;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.vybes.external.spotify.model.deserializer.search.SearchArtistResponseDeserializer;
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
