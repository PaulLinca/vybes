package com.vybes.external.spotify.model.deserializer;

import static com.vybes.external.spotify.model.deserializer.DeserializerHelper.getAlbum;
import static com.vybes.external.spotify.model.deserializer.DeserializerHelper.getArtists;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.vybes.external.spotify.model.entity.SpotifyAlbum;

import java.io.IOException;

public class AlbumDeserializer extends JsonDeserializer<SpotifyAlbum> {

    @Override
    public SpotifyAlbum deserialize(
            final JsonParser jsonParser, final DeserializationContext deserializationContext)
            throws IOException {

        JsonNode valueAsJson = jsonParser.getCodec().readTree(jsonParser);

        SpotifyAlbum album = getAlbum(valueAsJson);
        album.setArtists(getArtists(valueAsJson.withArray("artists")));

        return album;
    }
}
