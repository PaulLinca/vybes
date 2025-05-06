package com.vybes.controller;

import com.vybes.dto.CommentDTO;
import com.vybes.dto.CommentLikeDTO;
import com.vybes.dto.LikeDTO;
import com.vybes.dto.mapper.CommentMapper;
import com.vybes.dto.mapper.LikeMapper;
import com.vybes.dto.response.ErrorResponse;
import com.vybes.model.Comment;
import com.vybes.model.CommentLike;
import com.vybes.model.Post;
import com.vybes.model.PostLike;
import com.vybes.model.VybesUser;
import com.vybes.repository.UserRepository;
import com.vybes.service.post.PostService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final UserRepository userRepository;
    private final CommentMapper commentMapper;
    private final LikeMapper likeMapper;

    @PostMapping("/{postId}/likes")
    public ResponseEntity<?> likePost(@PathVariable Long postId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Post post = postService.getPostById(postId);

        PostLike like =
                PostLike.builder()
                        .user(userRepository.findByEmail(authentication.getName()).orElseThrow())
                        .post(post)
                        .build();

        Optional<LikeDTO> likeDto =
                Optional.ofNullable(postService.saveLike(like)).map(likeMapper::transform);
        if (likeDto.isPresent()) {
            return new ResponseEntity<>(likeDto.get(), HttpStatus.CREATED);
        }

        return ResponseEntity.badRequest()
                .body(
                        ErrorResponse.builder()
                                .message("User has already liked this post")
                                .status(400)
                                .build());
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{postId}/likes")
    public ResponseEntity<?> unlikePost(@PathVariable Long postId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Optional<PostLike> like =
                postService.deleteLike(
                        postId,
                        userRepository
                                .findByEmail(authentication.getName())
                                .map(VybesUser::getUserId)
                                .orElseThrow());

        if (like.isPresent()) {
            return like.map(l -> likeMapper.transform(l))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK))
                    .get();
        }

        return ResponseEntity.badRequest()
                .body(ErrorResponse.builder().message("Can't unlike post").status(400).build());
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{postId}/comments")
    public CommentDTO createComment(@PathVariable Long postId, @RequestBody CommentDTO request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Comment comment = commentMapper.transform(request);
        comment.setUser(userRepository.findByEmail(authentication.getName()).orElseThrow());
        comment.setPost(postService.getPostById(postId));

        return commentMapper.transform(postService.saveComment(comment));
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{postId}/comments/{commentId}")
    public ResponseEntity<?> deleteComment(
            @PathVariable Long postId, @PathVariable Long commentId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        boolean isDeleted =
                postService.deleteComment(
                        postId,
                        commentId,
                        userRepository
                                .findByEmail(authentication.getName())
                                .map(VybesUser::getUserId)
                                .orElseThrow());

        if (isDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{postId}/comments/{commentId}/likes")
    public ResponseEntity<?> likeComment(@PathVariable Long postId, @PathVariable Long commentId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        CommentLike like =
                CommentLike.builder()
                        .user(userRepository.findByEmail(authentication.getName()).orElseThrow())
                        .comment(postService.getCommentById(commentId))
                        .build();

        Optional<CommentLikeDTO> likeDto =
                Optional.ofNullable(postService.saveCommentLike(like))
                        .map(commentMapper::transform);
        if (likeDto.isPresent()) {
            return new ResponseEntity<>(likeDto.get(), HttpStatus.CREATED);
        }

        return ResponseEntity.badRequest()
                .body(
                        ErrorResponse.builder()
                                .message("User has already liked this comment")
                                .status(400)
                                .build());
    }

    @ResponseStatus(HttpStatus.CREATED)
    @DeleteMapping("/{postId}/comments/{commentId}/likes")
    public ResponseEntity<?> unlikeComment(
            @PathVariable Long postId, @PathVariable Long commentId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Long userId =
                userRepository
                        .findByEmail(authentication.getName())
                        .map(VybesUser::getUserId)
                        .orElseThrow();

        boolean isDeleted = postService.deleteCommentLike(commentId, userId);
        if (isDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{postId}/comments")
    public List<CommentDTO> getCommentsByPostId(@PathVariable Long postId) {
        return postService.getCommentsByPostId(postId).stream()
                .map(commentMapper::transform)
                .toList();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{postId}/likes")
    public List<LikeDTO> getLikesByPostId(@PathVariable Long postId) {
        return postService.getLikesByPostId(postId).stream().map(likeMapper::transform).toList();
    }
}
