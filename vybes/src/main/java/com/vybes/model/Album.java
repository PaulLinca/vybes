package com.vybes.model;

import jakarta.persistence.*;

import lombok.*;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@Builder
@Entity
@Table(name = "albums")
@NoArgsConstructor
@AllArgsConstructor
public class Album {

    @Id private String spotifyId;
    private String name;
    private String imageUrl;
    private LocalDate releaseDate;

    @ManyToOne
    @JoinColumn(name = "artist_id")
    private Artist artist;

    @ManyToMany(mappedBy = "favoriteAlbums")
    private Set<VybesUser> fans;
}
