package com.vybes.external.spotify.model.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.vybes.external.spotify.model.entity.SpotifyArtist;

import java.io.IOException;
import java.util.Optional;

public class ArtistDeserializer extends JsonDeserializer<SpotifyArtist> {

    @Override
    public SpotifyArtist deserialize(
            final JsonParser jsonParser, final DeserializationContext deserializationContext)
            throws IOException {

        JsonNode valueAsJson = jsonParser.getCodec().readTree(jsonParser);

        return SpotifyArtist.builder()
                .id(valueAsJson.get("id").textValue())
                .name(valueAsJson.get("name").textValue())
                .spotifyUrl(valueAsJson.get("external_urls").at("/spotify").textValue())
                .imageUrl(
                        Optional.ofNullable(valueAsJson.get("images"))
                                .map(ArrayNode.class::cast)
                                .map(n -> n.get(0))
                                .map(n -> n.get("url"))
                                .map(JsonNode::textValue)
                                .orElse(null))
                .build();
    }
}
