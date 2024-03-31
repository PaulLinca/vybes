package com.vybes.service.model.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.vybes.service.model.entity.Artist;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
                .imageUrl(
                        Optional.ofNullable(valueAsJson.get("images"))
                                .map(ArrayNode.class::cast)
                                .map(n -> n.get(0))
                                .map(n -> n.get("url"))
                                .map(JsonNode::textValue)
                                .orElse(null))
                .genres(getGenres(valueAsJson.get("genres")))
                .popularity(
                        Optional.ofNullable(valueAsJson.get("popularity"))
                                .map(JsonNode::intValue)
                                .orElse(null))
                .build();
    }

    private List<String> getGenres(JsonNode genresNode) {
        List<String> genres = new ArrayList<>();
        if (genresNode == null) {
            return genres;
        }

        for (JsonNode genre : genresNode) {
            genres.add(genre.textValue());
        }
        return genres;
    }
}
