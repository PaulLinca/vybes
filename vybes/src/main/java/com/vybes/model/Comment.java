package com.vybes.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;

import lombok.Data;

import java.time.ZonedDateTime;
import java.util.List;

@Data
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 500)
    private String text;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private VybesUser user;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @JsonManagedReference
    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CommentLike> likes;

    private ZonedDateTime postedDate;
}
