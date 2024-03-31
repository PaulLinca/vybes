package com.vybes.service.model.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.vybes.service.model.Artist;

import java.io.IOException;

public class ArtistDeserializer extends JsonDeserializer<Artist> {

    @Override
    public Artist deserialize(
            final JsonParser jsonParser, final DeserializationContext deserializationContext)
            throws IOException {

        JsonNode valueAsJson = jsonParser.getCodec().readTree(jsonParser);

        return Artist.builder()
                .id(valueAsJson.get("id").textValue())
                .name(valueAsJson.get("name").textValue())
                .spotifyUrl(valueAsJson.get("external_urls").at("/spotify").textValue())
                .build();
    }
}
