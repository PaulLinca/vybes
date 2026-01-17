package com.vybes.service.user;

import com.vybes.model.VybesUser;
import com.vybes.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserFollowService {

    private final UserRepository userRepository;

    @Transactional
    public void follow(Long targetUserId) {
        VybesUser currentUser = getAuthenticatedUser();
        VybesUser target =
                userRepository
                        .findByUserId(targetUserId)
                        .orElseThrow(() -> new IllegalArgumentException("Target user not found"));

        if (currentUser.getUserId().equals(target.getUserId())) {
            throw new IllegalArgumentException("Cannot follow yourself");
        }

        currentUser.getFollowing().add(target);
        target.getFollowers().add(currentUser);

        userRepository.save(currentUser);
        userRepository.save(target);
    }

    @Transactional
    public void unfollow(Long targetUserId) {
        VybesUser currentUser = getAuthenticatedUser();
        VybesUser target =
                userRepository
                        .findByUserId(targetUserId)
                        .orElseThrow(() -> new IllegalArgumentException("Target user not found"));

        if (currentUser.getUserId().equals(target.getUserId())) {
            return;
        }

        currentUser.getFollowing().remove(target);
        target.getFollowers().remove(currentUser);

        userRepository.save(currentUser);
        userRepository.save(target);
    }

    private VybesUser getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userRepository
                .findByEmail(authentication.getName())
                .orElseThrow(() -> new IllegalStateException("Authenticated user not found"));
    }
}
