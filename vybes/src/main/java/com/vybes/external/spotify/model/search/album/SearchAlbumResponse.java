package com.vybes.external.spotify.model.search.album;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.vybes.external.spotify.model.deserializer.SearchAlbumResponseDeserializer;
import com.vybes.external.spotify.model.entity.SpotifyAlbum;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@Builder
@ToString
@JsonDeserialize(using = SearchAlbumResponseDeserializer.class)
public class SearchAlbumResponse {
    private List<SpotifyAlbum> searchAlbumItems;
}
