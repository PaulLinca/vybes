package com.vybes.dto.request;

import jakarta.validation.constraints.AssertTrue;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChallengeOptionRequestDTO {

    private String spotifyAlbumId;
    private String spotifyTrackId;
    private String spotifyArtistId;
    private String customText;

    @AssertTrue(message = "Exactly one option field must be provided")
    public boolean isValidOption() {
        int nonNullCount = 0;
        if (spotifyAlbumId != null && !spotifyAlbumId.trim().isEmpty()) {
            nonNullCount++;
        }
        if (spotifyTrackId != null && !spotifyTrackId.trim().isEmpty()) {
            nonNullCount++;
        }
        if (spotifyArtistId != null && !spotifyArtistId.trim().isEmpty()) {
            nonNullCount++;
        }
        if (customText != null && !customText.trim().isEmpty()) {
            nonNullCount++;
        }

        return nonNullCount == 1;
    }
}
