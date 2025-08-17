package com.vybes.repository;

import com.vybes.model.Track;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrackRepository extends JpaRepository<Track, Long> {
    Track findBySpotifyId(String spotifyId);
}
