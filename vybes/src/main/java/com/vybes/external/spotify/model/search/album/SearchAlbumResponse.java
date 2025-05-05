package com.vybes.external.spotify.model.search.album;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.vybes.external.spotify.model.deserializer.SearchAlbumResponseDeserializer;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@JsonDeserialize(using = SearchAlbumResponseDeserializer.class)
public class SearchAlbumResponse {
    private List<SearchAlbumResult> searchAlbumItems;
}
