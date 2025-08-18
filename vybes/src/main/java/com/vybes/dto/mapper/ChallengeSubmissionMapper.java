package com.vybes.dto.mapper;

import com.vybes.dto.ChallengeSubmissionDTO;
import com.vybes.exception.BadRequestException;
import com.vybes.model.AnswerType;
import com.vybes.model.ChallengeSubmission;
import com.vybes.repository.AlbumRepository;
import com.vybes.repository.ArtistRepository;
import com.vybes.repository.TrackRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ChallengeSubmissionMapper {

    private final AlbumRepository albumRepository;
    private final ArtistRepository artistRepository;
    private final TrackRepository trackRepository;

    private final AlbumMapper albumMapper;
    private final ArtistMapper artistMapper;
    private final TrackMapper trackMapper;

    private final UserMapper userMapper;

    public ChallengeSubmissionDTO transformToDTO(
            ChallengeSubmission submission, AnswerType answerType) {
        ChallengeSubmissionDTO dto =
                ChallengeSubmissionDTO.builder()
                        .id(submission.getId())
                        .user(userMapper.transformToDTO(submission.getUser()))
                        .submittedAt(submission.getSubmittedAt())
                        .votesCount(
                                Optional.ofNullable(submission.getVotes())
                                        .orElse(Collections.emptyList())
                                        .size())
                        .build();

        resolveOption(submission, dto, answerType);

        return dto;
    }

    private void resolveOption(
            ChallengeSubmission submission, ChallengeSubmissionDTO dto, AnswerType answerType) {
        switch (answerType) {
            case ALBUM:
                dto.setAlbum(
                        albumRepository
                                .findById(submission.getAlbumId())
                                .map(albumMapper::transformToDTO)
                                .orElse(null));
                break;
            case TRACK:
                dto.setTrack(
                        trackRepository
                                .findById(submission.getTrackId())
                                .map(trackMapper::transformToDTO)
                                .orElse(null));
                break;
            case ARTIST:
                dto.setArtist(
                        artistRepository
                                .findById(submission.getArtistId())
                                .map(artistMapper::transformToDTO)
                                .orElse(null));
                break;
            case CUSTOM_TEXT:
                dto.setCustomText(submission.getCustomText());
                break;
            default:
                throw new BadRequestException("Unsupported answer type: " + answerType);
        }
    }
}
