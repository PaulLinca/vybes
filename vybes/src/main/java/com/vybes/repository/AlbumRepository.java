package com.vybes.repository;

import com.vybes.model.Album;
import com.vybes.model.MusicProvider;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AlbumRepository extends JpaRepository<Album, Long> {
    Optional<Album> findByExternalId(String externalId);

    Optional<Album> findByExternalIdAndProvider(String externalId, MusicProvider provider);
}
