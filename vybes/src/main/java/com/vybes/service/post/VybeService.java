package com.vybes.service.post;

import com.vybes.dto.request.CreateVybeRequestDTO;
import com.vybes.model.*;
import com.vybes.repository.PostRepository;
import com.vybes.service.music.MusicService;

import jakarta.transaction.Transactional;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
@Transactional
public class VybeService {

    private final MusicService musicService;
    private final PostRepository postRepository;

    public Vybe createVybe(CreateVybeRequestDTO request, VybesUser user) {
        Track track = musicService.getOrCreateTrack(request.getSpotifyTrackId());

        Vybe vybe =
                Vybe.builder()
                        .track(track)
                        .album(track.getAlbum())
                        .description(request.getDescription())
                        .user(user)
                        .likes(new ArrayList<>())
                        .comments(new ArrayList<>())
                        .postedDate(ZonedDateTime.now())
                        .build();

        return postRepository.save(vybe);
    }

    public Vybe getVybe(Long vybeId) {
        return postRepository
                .findById(vybeId)
                .filter(Vybe.class::isInstance)
                .map(Vybe.class::cast)
                .orElseThrow(() -> new IllegalArgumentException("Vybe not found"));
    }
}
