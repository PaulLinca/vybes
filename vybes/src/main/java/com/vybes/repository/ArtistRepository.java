package com.vybes.repository;

import com.vybes.model.Artist;
import com.vybes.model.MusicProvider;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ArtistRepository extends JpaRepository<Artist, Long> {
    Optional<Artist> findByExternalId(String externalId);

    Optional<Artist> findByExternalIdAndProvider(String externalId, MusicProvider provider);
}
