package com.vybes.external.spotify.model.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.vybes.external.spotify.model.entity.SpotifyAlbum;
import com.vybes.external.spotify.model.entity.SpotifyArtist;
import com.vybes.external.spotify.model.entity.SpotifyTrack;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.vybes.external.spotify.model.deserializer.DeserializerHelper.*;

@Slf4j
public class AlbumDeserializer extends JsonDeserializer<SpotifyAlbum> {

    @Override
    public SpotifyAlbum deserialize(
            final JsonParser jsonParser, final DeserializationContext deserializationContext)
            throws IOException {

        JsonNode valueAsJson = jsonParser.getCodec().readTree(jsonParser);
        SpotifyAlbum album = getAlbum(valueAsJson);
        album.setArtists(getArtists(valueAsJson.withArray("artists")));
        album.setTracks(getTracks(valueAsJson.get("tracks").withArray("items")));

        return album;
    }

    private List<SpotifyTrack> getTracks(JsonNode tracksNode) {
        List<SpotifyTrack> tracks = new ArrayList<>();
        for (JsonNode trackNode : tracksNode) {
            tracks.add(getBaseTrack(trackNode));
        }
        return tracks;
    }
}
