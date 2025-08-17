package com.vybes.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@DiscriminatorValue("ALBUM_REVIEW")
public class AlbumReview extends Post {
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "album_id", nullable = false)
    private Album album;

    private Integer score;

    @JsonManagedReference
    @OneToMany(mappedBy = "albumReview", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TrackReview> trackReviews;
}
