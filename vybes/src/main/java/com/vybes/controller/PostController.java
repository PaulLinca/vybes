package com.vybes.controller;

import com.vybes.service.writer.PostService;
import com.vybes.service.writer.entity.Post;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @GetMapping(value = "/findAll", produces = "application/json; charset=UTF-8")
    public List<Post> getAllPosts() {
        return postService.getAllPosts();
    }

    @PostMapping(value = "/post", produces = "application/json; charset=UTF-8")
    public void postTrack(@RequestParam String songName) {
        postService.addPost(songName);
    }
}
