package com.vybes.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class TrackReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "track_id", nullable = false)
    private Track track;

    private boolean isFavorite;

    @Enumerated(EnumType.STRING)
    private TrackRating rating;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "album_review_id", nullable = false)
    private AlbumReview albumReview;

    public enum TrackRating {
        AWFUL,
        MEH,
        OKAY,
        GREAT,
        AMAZING
    }
}
