package com.vybes.repository;

import com.vybes.model.MusicProvider;
import com.vybes.model.Track;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TrackRepository extends JpaRepository<Track, Long> {
    Optional<Track> findByExternalId(String externalId);

    Optional<Track> findByExternalIdAndProvider(String externalId, MusicProvider provider);
}
