package com.vybes.external.spotify.model.deserializer;

import static com.vybes.external.spotify.model.deserializer.DeserializerHelper.getAlbum;
import static com.vybes.external.spotify.model.deserializer.DeserializerHelper.getArtist;
import static com.vybes.external.spotify.model.deserializer.DeserializerHelper.getArtists;
import static com.vybes.external.spotify.model.deserializer.DeserializerHelper.getTrack;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.vybes.external.spotify.model.entity.SpotifyAlbum;
import com.vybes.external.spotify.model.entity.SpotifyArtist;
import com.vybes.external.spotify.model.entity.SpotifyTrack;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TrackDeserializer extends JsonDeserializer<SpotifyTrack> {

    @Override
    public SpotifyTrack deserialize(
            final JsonParser jsonParser, final DeserializationContext deserializationContext)
            throws IOException {
        return getTrack(jsonParser.getCodec().readTree(jsonParser));
    }
}
