package com.vybes.external.spotify.model.deserializer;

import static com.vybes.external.spotify.model.deserializer.DeserializerHelper.getArtist;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.vybes.external.spotify.model.entity.SpotifyArtist;

import java.io.IOException;

public class ArtistDeserializer extends JsonDeserializer<SpotifyArtist> {

    @Override
    public SpotifyArtist deserialize(
            final JsonParser jsonParser, final DeserializationContext deserializationContext)
            throws IOException {
        return getArtist(jsonParser.getCodec().readTree(jsonParser));
    }
}
