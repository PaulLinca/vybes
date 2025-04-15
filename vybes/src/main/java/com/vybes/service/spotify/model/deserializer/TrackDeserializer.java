package com.vybes.service.spotify.model.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.vybes.service.spotify.model.entity.SpotifyAlbum;
import com.vybes.service.spotify.model.entity.SpotifyArtist;
import com.vybes.service.spotify.model.entity.SpotifyTrack;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TrackDeserializer extends JsonDeserializer<SpotifyTrack> {

    @Override
    public SpotifyTrack deserialize(
            final JsonParser jsonParser, final DeserializationContext deserializationContext)
            throws IOException {

        JsonNode valueAsJson = jsonParser.getCodec().readTree(jsonParser);

        return SpotifyTrack.builder()
                .id(valueAsJson.get("id").textValue())
                .name(valueAsJson.get("name").textValue())
                .spotifyUrl(valueAsJson.get("external_urls").at("/spotify").textValue())
                .album(
                        jsonParser
                                .getCodec()
                                .treeToValue(valueAsJson.get("album"), SpotifyAlbum.class))
                .artists(getArtists(valueAsJson.withArray("artists"), jsonParser.getCodec()))
                .build();
    }

    private List<SpotifyArtist> getArtists(ArrayNode artistsAsJson, ObjectCodec codec)
            throws JsonProcessingException {
        List<SpotifyArtist> artists = new ArrayList<>();
        for (JsonNode artistNode : artistsAsJson) {
            artists.add(codec.treeToValue(artistNode, SpotifyArtist.class));
        }
        return artists;
    }
}
