package com.vybes.controller;

import static org.mockito.Mockito.verify;

import com.vybes.service.spotify.SpotifyService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SpotifyControllerTest {
    private static final String STRING_PARAMETER = "param";

    @Mock private SpotifyService spotifyService;

    @InjectMocks private SpotifyController testee;

    @Test
    void test() {
        testee.searchTrack(STRING_PARAMETER);
        testee.getTrack(STRING_PARAMETER);
        testee.getAlbum(STRING_PARAMETER);
        testee.getArtist(STRING_PARAMETER);

        verify(spotifyService).searchTrack(STRING_PARAMETER);
        verify(spotifyService).getTrack(STRING_PARAMETER);
        verify(spotifyService).getAlbum(STRING_PARAMETER);
        verify(spotifyService).getArtist(STRING_PARAMETER);
    }
}
