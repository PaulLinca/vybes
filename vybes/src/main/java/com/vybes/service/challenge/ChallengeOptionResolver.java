package com.vybes.service.challenge;

import com.vybes.dto.request.ChallengeOptionRequestDTO;
import com.vybes.exception.BadRequestException;
import com.vybes.model.AnswerType;
import com.vybes.model.Challenge;
import com.vybes.model.ChallengeOption;
import com.vybes.service.music.MusicService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ChallengeOptionResolver {
    private final MusicService musicService;

    public List<ChallengeOption> resolveOptions(
            List<ChallengeOptionRequestDTO> optionRequests,
            AnswerType answerType,
            Challenge challenge) {
        List<ChallengeOption> options = new ArrayList<>();

        for (ChallengeOptionRequestDTO optionRequest : optionRequests) {
            ChallengeOption option =
                    ChallengeOption.builder().votes(new ArrayList<>()).challenge(challenge).build();

            switch (answerType) {
                case ALBUM:
                    if (optionRequest.getSpotifyAlbumId() != null) {
                        option.setAlbumId(
                                musicService
                                        .getOrCreateAlbum(optionRequest.getSpotifyAlbumId())
                                        .getId());
                    } else {
                        throw new BadRequestException("Album ID required for ALBUM challenge type");
                    }
                    break;

                case TRACK:
                    if (optionRequest.getSpotifyTrackId() != null) {
                        option.setTrackId(
                                musicService
                                        .getOrCreateTrack(optionRequest.getSpotifyTrackId())
                                        .getId());
                    } else {
                        throw new BadRequestException("Track ID required for TRACK challenge type");
                    }
                    break;

                case ARTIST:
                    if (optionRequest.getSpotifyArtistId() != null) {
                        option.setArtistId(
                                musicService
                                        .getOrCreateArtist(optionRequest.getSpotifyArtistId())
                                        .getId());
                    } else {
                        throw new BadRequestException(
                                "Artist ID required for ARTIST challenge type");
                    }
                    break;

                case CUSTOM_TEXT:
                    if (optionRequest.getCustomText() != null
                            && !optionRequest.getCustomText().trim().isEmpty()) {
                        option.setCustomText(optionRequest.getCustomText().trim());
                    } else {
                        throw new BadRequestException(
                                "Custom text required for CUSTOM_TEXT challenge type");
                    }
                    break;

                default:
                    throw new BadRequestException("Unsupported answer type: " + answerType);
            }

            options.add(option);
        }

        return options;
    }
}
