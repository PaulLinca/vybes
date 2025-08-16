package com.vybes.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue("VYBE")
public class Vybe extends Post {
    private String songName;
    private String spotifyId;
    private String spotifyAlbumId;
    private String imageUrl;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "vybe_artist",
            joinColumns = @JoinColumn(name = "vybe_id"),
            inverseJoinColumns = @JoinColumn(name = "artist_id"))
    private List<Artist> spotifyArtists;
}
