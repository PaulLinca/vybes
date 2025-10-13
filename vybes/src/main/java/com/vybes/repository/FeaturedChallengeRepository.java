package com.vybes.repository;

import com.vybes.model.Challenge;
import com.vybes.model.FeaturedChallenge;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface FeaturedChallengeRepository extends JpaRepository<FeaturedChallenge, Long> {

    @Query(
            "SELECT fc FROM FeaturedChallenge fc WHERE fc.isActive = true AND fc.featuredAt <= :now AND fc.featuredUntil > :now")
    Optional<FeaturedChallenge> findCurrentFeaturedChallenge(@Param("now") ZonedDateTime now);

    @Query(
            "SELECT fc FROM FeaturedChallenge fc WHERE fc.isActive = true AND fc.featuredUntil < :now")
    List<FeaturedChallenge> findExpiredFeaturedChallenges(@Param("now") ZonedDateTime now);

    @Query(
            "SELECT fc FROM FeaturedChallenge fc WHERE fc.featuredType = :type AND fc.isActive = true ORDER BY fc.featuredAt DESC")
    List<FeaturedChallenge> findByFeaturedType(@Param("type") FeaturedChallenge.FeaturedType type);

    Optional<FeaturedChallenge> findByChallengeAndIsActiveTrue(Challenge challenge);

    @Query(
            "SELECT fc FROM FeaturedChallenge fc WHERE fc.isActive = true ORDER BY fc.featuredAt DESC")
    List<FeaturedChallenge> findAllActiveFeaturedChallenges();

    @Query(
            "SELECT COUNT(fc) > 0 FROM FeaturedChallenge fc WHERE fc.challenge = :challenge AND fc.isActive = true AND fc.featuredAt <= :now AND fc.featuredUntil > :now")
    boolean isChallengeFeatured(
            @Param("challenge") Challenge challenge, @Param("now") ZonedDateTime now);

    @Query(
            "SELECT fc FROM FeaturedChallenge fc WHERE fc.featuredUntil < :now ORDER BY fc.featuredUntil DESC")
    Page<FeaturedChallenge> findPastFeaturedChallenges(
            @Param("now") ZonedDateTime now, Pageable pageable);
}
