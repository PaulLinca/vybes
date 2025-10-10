package com.vybes.service.challenge;

import com.vybes.exception.BadRequestException;
import com.vybes.model.AnswerType;
import com.vybes.model.Challenge;
import com.vybes.model.ChallengeOption;
import com.vybes.service.music.MusicService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ChallengeOptionResolver {
    private final MusicService musicService;

    public List<ChallengeOption> resolveOptions(
            @Valid List<String> optionRequests, AnswerType answerType, Challenge challenge) {
        List<ChallengeOption> options = new ArrayList<>();

        for (String optionRequest : optionRequests) {
            ChallengeOption option =
                    ChallengeOption.builder().votes(new ArrayList<>()).challenge(challenge).build();

            switch (answerType) {
                case ALBUM:
                    try {
                        option.setAlbumId(musicService.getOrCreateAlbum(optionRequest).getId());
                    } catch (Exception e) {
                        throw new BadRequestException("Album ID required for ALBUM challenge type");
                    }
                    break;

                case TRACK:
                    try {
                        option.setTrackId(musicService.getOrCreateTrack(optionRequest).getId());
                    } catch (Exception e) {
                        throw new BadRequestException("Track ID required for TRACK challenge type");
                    }
                    break;

                case ARTIST:
                    try {
                        option.setArtistId(musicService.getOrCreateArtist(optionRequest).getId());
                    } catch (Exception e) {
                        throw new BadRequestException(
                                "Artist ID required for ARTIST challenge type");
                    }
                    break;

                case CUSTOM_TEXT:
                    if (option != null && !optionRequest.trim().isEmpty()) {
                        option.setCustomText(optionRequest.trim());
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
