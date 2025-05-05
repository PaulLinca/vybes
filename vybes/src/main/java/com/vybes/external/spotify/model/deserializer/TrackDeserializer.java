package com.vybes.external.spotify.model.deserializer;

import static com.vybes.external.spotify.model.deserializer.DeserializerHelper.getTrack;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.vybes.external.spotify.model.entity.SpotifyTrack;

import java.io.IOException;

public class TrackDeserializer extends JsonDeserializer<SpotifyTrack> {

    @Override
    public SpotifyTrack deserialize(
            final JsonParser jsonParser, final DeserializationContext deserializationContext)
            throws IOException {
        return getTrack(jsonParser.getCodec().readTree(jsonParser));
    }
}
