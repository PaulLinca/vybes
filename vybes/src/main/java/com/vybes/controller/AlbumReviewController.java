package com.vybes.controller;

import com.vybes.dto.AlbumReviewDTO;
import com.vybes.dto.mapper.PostMapper;
import com.vybes.dto.request.CreateAlbumReviewRequestDTO;
import com.vybes.exception.UserNotFoundException;
import com.vybes.model.AlbumReview;
import com.vybes.model.VybesUser;
import com.vybes.repository.UserRepository;
import com.vybes.service.post.AlbumReviewService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/album-reviews")
@RequiredArgsConstructor
public class AlbumReviewController {

    private final PostMapper postMapper;
    private final UserRepository userRepository;
    private final AlbumReviewService albumReviewService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/post", produces = "application/json; charset=UTF-8")
    public AlbumReviewDTO postAlbumReview(@RequestBody CreateAlbumReviewRequestDTO request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        VybesUser user = getUser(authentication.getName());

        AlbumReview albumReview = albumReviewService.createAlbumReview(request, user);

        return postMapper.transformToDTO(albumReview);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{albumReviewId}", produces = "application/json; charset=UTF-8")
    public AlbumReviewDTO getAlbumReview(@PathVariable Long albumReviewId) {
        AlbumReview albumReview = albumReviewService.getAlbumReview(albumReviewId);

        return postMapper.transformToDTO(albumReview);
    }

    private VybesUser getUser(String name) {
        return userRepository
                .findByEmail(name)
                .orElseThrow(() -> new UserNotFoundException("User not found: " + name));
    }
}
