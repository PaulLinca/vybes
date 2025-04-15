package com.vybes.service.spotify.model.search.artist;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.vybes.service.spotify.model.deserializer.SearchArtistResponseDeserializer;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@JsonDeserialize(using = SearchArtistResponseDeserializer.class)
public class SearchArtistResponse {
    private List<SearchArtistItem> searchArtistItems;
}
