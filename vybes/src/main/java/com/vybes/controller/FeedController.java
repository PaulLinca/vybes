package com.vybes.controller;

import com.vybes.dto.PostDTO;
import com.vybes.dto.mapper.PostMapper;
import com.vybes.dto.response.PostPageResponse;
import com.vybes.model.Post;
import com.vybes.model.VybesUser;
import com.vybes.repository.UserRepository;
import com.vybes.service.post.PostService;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/feed")
@RequiredArgsConstructor
public class FeedController {

    private final PostService postService;
    private final PostMapper postMapper;
    private final UserRepository userRepository;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(produces = "application/json; charset=UTF-8")
    public ResponseEntity<PostPageResponse> getPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "postedDate") String sort,
            @RequestParam(defaultValue = "DESC") String direction) {

        Page<Post> postPage = postService.getPostsPaginated(page, size, sort, direction);

        List<PostDTO> dtos =
                postPage.getContent().stream().map(postMapper::transformToDTO).toList();

        PostPageResponse response =
                new PostPageResponse(
                        dtos,
                        postPage.getNumber(),
                        postPage.getSize(),
                        postPage.getTotalElements(),
                        postPage.getTotalPages(),
                        postPage.isLast());

        return ResponseEntity.ok(response);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/all", produces = "application/json; charset=UTF-8")
    public List<PostDTO> getAllPosts() {
        return postService.getAllPosts().stream().map(postMapper::transformToDTO).toList();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/following", produces = "application/json; charset=UTF-8")
    public ResponseEntity<PostPageResponse> getFollowingFeed(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "postedDate") String sort,
            @RequestParam(defaultValue = "DESC") String direction) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        VybesUser currentUser =
                userRepository
                        .findByEmail(authentication.getName())
                        .orElseThrow(() -> new IllegalStateException("Authenticated user not found"));

        Page<Post> postPage =
                postService.getFollowingFeedPaginated(currentUser, page, size, sort, direction);

        List<PostDTO> dtos =
                postPage.getContent().stream().map(postMapper::transformToDTO).toList();

        PostPageResponse response =
                new PostPageResponse(
                        dtos,
                        postPage.getNumber(),
                        postPage.getSize(),
                        postPage.getTotalElements(),
                        postPage.getTotalPages(),
                        postPage.isLast());

        return ResponseEntity.ok(response);
    }
}
