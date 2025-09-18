package com.vybes.dto.mapper;

import com.vybes.dto.ChallengeOptionDTO;
import com.vybes.exception.BadRequestException;
import com.vybes.model.AnswerType;
import com.vybes.model.ChallengeOption;
import com.vybes.repository.AlbumRepository;
import com.vybes.repository.ArtistRepository;
import com.vybes.repository.TrackRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChallengeOptionMapper {

    private final AlbumRepository albumRepository;
    private final ArtistRepository artistRepository;
    private final TrackRepository trackRepository;

    private final AlbumMapper albumMapper;
    private final ArtistMapper artistMapper;
    private final TrackMapper trackMapper;

    public ChallengeOptionDTO transformToDTO(
            ChallengeOption option, AnswerType answerType, Long currentUserId) {
        ChallengeOptionDTO dto =
                ChallengeOptionDTO.builder()
                        .id(option.getId())
                        .votesCount(option.getVotes().size())
                        .votedByUser(
                                option.getVotes().stream()
                                        .anyMatch(
                                                vote ->
                                                        vote.getUser()
                                                                .getUserId()
                                                                .equals(currentUserId)))
                        .build();

        resolveOption(option, dto, answerType);

        return dto;
    }

    private void resolveOption(
            ChallengeOption option, ChallengeOptionDTO dto, AnswerType answerType) {
        switch (answerType) {
            case ALBUM:
                dto.setAlbum(
                        albumRepository
                                .findById(option.getAlbumId())
                                .map(albumMapper::transformToDTO)
                                .orElse(null));
                break;
            case TRACK:
                dto.setTrack(
                        trackRepository
                                .findById(option.getTrackId())
                                .map(trackMapper::transformToDTO)
                                .orElse(null));
                break;
            case ARTIST:
                dto.setArtist(
                        artistRepository
                                .findById(option.getArtistId())
                                .map(artistMapper::transformToDTO)
                                .orElse(null));
                break;
            case CUSTOM_TEXT:
                dto.setCustomText(option.getCustomText());
                break;
            default:
                throw new BadRequestException("Unsupported answer type: " + answerType);
        }
    }
}
