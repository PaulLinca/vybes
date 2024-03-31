package com.vybes.service.model.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.vybes.service.model.Album;
import com.vybes.service.model.Artist;
import com.vybes.service.model.Track;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TrackDeserializer extends JsonDeserializer<Track> {

    @Override
    public Track deserialize(
            final JsonParser jsonParser, final DeserializationContext deserializationContext)
            throws IOException {

        JsonNode valueAsJson = jsonParser.getCodec().readTree(jsonParser);

        return Track.builder()
                .id(valueAsJson.get("id").textValue())
                .name(valueAsJson.get("name").textValue())
                .spotifyUrl(valueAsJson.get("external_urls").at("/spotify").textValue())
                .album(jsonParser.getCodec().treeToValue(valueAsJson.get("album"), Album.class))
                .artists(getArtists(valueAsJson.withArray("artists"), jsonParser.getCodec()))
                .build();
    }

    private List<Artist> getArtists(ArrayNode artistsAsJson, ObjectCodec codec)
            throws JsonProcessingException {
        List<Artist> artists = new ArrayList<>();
        for (JsonNode artistNode : artistsAsJson) {
            artists.add(codec.treeToValue(artistNode, Artist.class));
        }
        return artists;
    }
}
