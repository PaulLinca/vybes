package com.vybes.controller;

import com.vybes.dto.PostDTO;
import com.vybes.dto.mapper.PostMapper;
import com.vybes.dto.response.PostPageResponse;
import com.vybes.model.Post;
import com.vybes.service.post.PostService;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(produces = "application/json; charset=UTF-8")
    public ResponseEntity<PostPageResponse> getPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sort,
            @RequestParam(defaultValue = "DESC") String direction) {

        Page<Post> vybesPage = postService.getPostsPaginated(page, size, sort, direction);

        List<PostDTO> vybesDTOs =
                vybesPage.getContent().stream().map(postMapper::toPostDTO).toList();

        PostPageResponse response =
                new PostPageResponse(
                        vybesDTOs,
                        vybesPage.getNumber(),
                        vybesPage.getSize(),
                        vybesPage.getTotalElements(),
                        vybesPage.getTotalPages(),
                        vybesPage.isLast());

        return ResponseEntity.ok(response);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/all", produces = "application/json; charset=UTF-8")
    public List<PostDTO> getAllPosts() {
        return postService.getAllPosts().stream().map(postMapper::toPostDTO).toList();
    }
}
