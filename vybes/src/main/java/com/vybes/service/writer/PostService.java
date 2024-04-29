package com.vybes.service.writer;

import com.vybes.service.writer.entity.Post;
import com.vybes.service.writer.repository.PostRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    public void addPost(String songName) {
        postRepository.save(Post.builder().id(22).songName(songName).build());
    }

    public List<Post> getAllPosts() {
        return postRepository.getAllPosts();
    }
}
