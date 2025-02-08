package com.vybes.service.vybe.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.vybes.service.user.model.VybesUser;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.List;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Vybe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String songName;

    private String spotifyTrackId;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "vybe_artist", // This is the name of the join table
            joinColumns = @JoinColumn(name = "vybe_id"), // Foreign key for Vybe
            inverseJoinColumns = @JoinColumn(name = "artist_id") // Foreign key for Artist
    )
    private List<Artist> spotifyArtists;
    private String spotifyAlbumId;

    private String imageUrl;

    private ZonedDateTime postedDate;

    private String description;

    @JsonManagedReference
    @OneToMany(mappedBy = "vybe", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Like> likes;

    @JsonManagedReference
    @OneToMany(mappedBy = "vybe", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Comment> comments;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private VybesUser user;
}
