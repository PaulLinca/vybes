package com.vybes.model;

import jakarta.persistence.*;

import lombok.*;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class ChallengeVote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "option_id")
    private ChallengeOption option;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "submission_id")
    private ChallengeSubmission submission;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private VybesUser user;
}
