package com.vybes.service.post;

import com.vybes.model.*;
import com.vybes.repository.CommentLikeRepository;
import com.vybes.repository.CommentRepository;
import com.vybes.repository.PostLikeRepository;
import com.vybes.repository.PostRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final PostLikeRepository postLikeRepository;
    private final CommentLikeRepository commentLikeRepository;

    @Transactional
    public Post createPost(Post post) {
        postRepository.save(post);
        return post;
    }

    @Transactional
    public Page<Post> getPostsPaginated(int page, int size, String sortBy, String direction) {
        if (page < 0) page = 0;
        if (size < 1) size = 10;
        if (size > 50) size = 50;

        if (sortBy == null || sortBy.isEmpty()) {
            sortBy = "postedDate";
        }

        Sort.Direction sortDirection = Sort.Direction.DESC;
        if (direction != null && direction.equalsIgnoreCase("ASC")) {
            sortDirection = Sort.Direction.ASC;
        }

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortBy));

        return postRepository.findAllByOrderByPostedDateDesc(pageable);
    }

    @Transactional
    public Page<Post> getPostsPaginatedByUser(
            VybesUser user, int page, int size, String sortBy, String direction) {
        if (page < 0) page = 0;
        if (size < 1) size = 10;
        if (size > 50) size = 50;

        if (sortBy == null || sortBy.isEmpty()) {
            sortBy = "postedDate";
        }

        Sort.Direction sortDirection = Sort.Direction.DESC;
        if (direction != null && direction.equalsIgnoreCase("ASC")) {
            sortDirection = Sort.Direction.ASC;
        }

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortBy));

        return postRepository.findByUserOrderByPostedDateDesc(user, pageable);
    }

    @Transactional
    public Page<Post> getFollowingFeedPaginated(
            VybesUser currentUser, int page, int size, String sortBy, String direction) {
        if (page < 0) page = 0;
        if (size < 1) size = 10;
        if (size > 50) size = 50;

        if (sortBy == null || sortBy.isEmpty()) {
            sortBy = "postedDate";
        }

        Sort.Direction sortDirection = Sort.Direction.DESC;
        if (direction != null && direction.equalsIgnoreCase("ASC")) {
            sortDirection = Sort.Direction.ASC;
        }

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortBy));

        Set<VybesUser> relevantUsers = new HashSet<>(currentUser.getFollowers());
        relevantUsers.add(currentUser);

        return postRepository.findByUserInOrderByPostedDateDesc(relevantUsers, pageable);
    }

    @Transactional
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    @Transactional
    public Post getPostById(Long id) {
        return postRepository.findById(id).orElseThrow();
    }

    @Transactional
    public Comment getCommentById(Long id) {
        return commentRepository.findById(id).orElseThrow();
    }

    @Transactional
    public Comment saveComment(Comment comment) {
        return commentRepository.save(comment);
    }

    @Transactional
    public boolean deleteComment(Long postId, Long commentId, Long userId) {
        int rowsDeleted =
                commentRepository.deleteByCommentIdAndPostIdAndUserId(commentId, postId, userId);
        return rowsDeleted > 0;
    }

    @Transactional
    public List<Comment> getCommentsByPostId(Long postId) {
        return commentRepository.findByPostId(postId);
    }

    @Transactional
    public PostLike saveLike(PostLike like) {

        if (!postLikeRepository
                .findByPostIdAndUser_UserId(like.getPost().getId(), like.getUser().getUserId())
                .isEmpty()) {
            return null;
        }

        return postLikeRepository.save(like);
    }

    @Transactional
    public Optional<PostLike> deleteLike(Long postId, Long userId) {
        return postLikeRepository.deleteByPostIdAndUser_UserId(postId, userId).stream().findFirst();
    }

    @Transactional
    public CommentLike saveCommentLike(CommentLike like) {
        if (commentLikeRepository.findByCommentIdAndUserId(like.getId(), like.getUser().getUserId())
                != null) {
            return null;
        }

        return commentLikeRepository.save(like);
    }

    @Transactional
    public boolean deleteCommentLike(Long commentId, Long userId) {
        int rowsDeleted = commentLikeRepository.deleteByCommentIdAndUserId(commentId, userId);
        return rowsDeleted > 0;
    }

    @Transactional
    public List<PostLike> getLikesByPostId(Long postId) {
        return postLikeRepository.findByPostId(postId);
    }

    @Transactional
    public void deletePost(Long postId, Long userId) throws AccessDeniedException {
        Post post =
                postRepository
                        .findById(postId)
                        .orElseThrow(() -> new EntityNotFoundException("Post not found"));

        if (!post.getUser().getUserId().equals(userId)) {
            throw new AccessDeniedException("You are not allowed to delete this post.");
        }

        postRepository.delete(post);
    }
}
