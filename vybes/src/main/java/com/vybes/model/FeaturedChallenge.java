package com.vybes.model;

import jakarta.persistence.*;

import lombok.*;

import java.time.ZonedDateTime;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "featured_challenges")
public class FeaturedChallenge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "challenge_id", nullable = false, unique = true)
    private Challenge challenge;

    @Column(nullable = false)
    private ZonedDateTime featuredAt;

    @Column(nullable = false)
    private ZonedDateTime featuredUntil;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FeaturedType featuredType;

    @Column(nullable = false)
    @Builder.Default
    private Boolean isActive = true;

    public enum FeaturedType {
        DAILY,
        WEEKLY,
        SPECIAL_EVENT
    }
}
