package com.vybes.service.review.album.entity;

import java.time.ZonedDateTime;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.vybes.service.Post;
import com.vybes.service.user.model.VybesUser;
import com.vybes.service.vybe.entity.Artist;
import com.vybes.service.vybe.entity.Comment;
import com.vybes.service.vybe.entity.Like;
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
import lombok.ToString;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class AlbumReview implements Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String albumName;

    private String spotifyAlbumId;

    private Integer score;

    private String imageUrl;

    private ZonedDateTime postedDate;

    private String description;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "album_review_artist",
            joinColumns = @JoinColumn(name = "album_review_id"),
            inverseJoinColumns = @JoinColumn(name = "artist_id")
    )
    private List<Artist> spotifyArtists;

    @JsonManagedReference
    @OneToMany(mappedBy = "albumReview", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Like> likes;

    @JsonManagedReference
    @OneToMany(mappedBy = "albumReview", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Comment> comments;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private VybesUser user;
}