package com.vybes.service.model.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.vybes.service.model.Album;
import com.vybes.service.model.Artist;

import java.io.IOException;

public class AlbumDeserializer extends JsonDeserializer<Album> {

    @Override
    public Album deserialize(
            final JsonParser jsonParser, final DeserializationContext deserializationContext)
            throws IOException {

        JsonNode valueAsJson = jsonParser.getCodec().readTree(jsonParser);

        return Album.builder()
                .id(valueAsJson.get("id").textValue())
                .name(valueAsJson.get("name").textValue())
                .spotifyUrl(valueAsJson.get("external_urls").get("spotify").textValue())
                .imageUrl(valueAsJson.get("images").get(0).get("url").textValue())
                .releaseDate(valueAsJson.get("release_date").textValue())
                .artist(jsonParser.getCodec().treeToValue(valueAsJson.get("artists").get(0), Artist.class))
                .build();
    }
}
