package com.vybes.external.spotify.model.deserializer.search;

import static com.vybes.external.spotify.model.deserializer.DeserializerHelper.getAlbum;
import static com.vybes.external.spotify.model.deserializer.DeserializerHelper.getArtists;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.vybes.external.spotify.model.entity.SpotifyAlbum;
import com.vybes.external.spotify.model.entity.search.SearchAlbumResponse;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class SearchAlbumResponseDeserializer extends JsonDeserializer<SearchAlbumResponse> {

    @Override
    public SearchAlbumResponse deserialize(JsonParser jsonParser, DeserializationContext ctxt)
            throws IOException {
        JsonNode rootNode = jsonParser.getCodec().readTree(jsonParser);
        JsonNode itemsNode = rootNode.get("albums").get("items");

        List<SpotifyAlbum> albums = new ArrayList<>();
        for (JsonNode albumNode : itemsNode) {
            if (albumNode.get("album_type").asText().equals("single")) {
                continue;
            }
            SpotifyAlbum album = getAlbum(albumNode);
            album.setArtists(getArtists(albumNode.get("artists")));
            albums.add(album);
        }

        return SearchAlbumResponse.builder().searchAlbumItems(albums).build();
    }
}
