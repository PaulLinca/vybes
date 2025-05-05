package com.vybes.service.post;

import com.vybes.model.Comment;
import com.vybes.model.Like;
import com.vybes.model.Vybe;
import com.vybes.repository.CommentRepository;
import com.vybes.repository.LikeRepository;
import com.vybes.repository.post.VybeRepository;

import jakarta.transaction.Transactional;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VybeService {
    private final VybeRepository vybeRepository;
    private final LikeRepository likeRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public Vybe createVybe(Vybe vybe) {
        vybeRepository.save(vybe);
        return vybe;
    }

    @Transactional
    public Page<Vybe> getVybesPaginated(int page, int size, String sortBy, String direction) {
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

        return vybeRepository.findAll(pageable);
    }


    @Transactional
    public List<Vybe> getAllVybes() {
        return vybeRepository.findAll();
    }

    @Transactional
    public Vybe getVybeById(Long id) {
        return vybeRepository.findById(id).orElseThrow();
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
    public boolean deleteComment(Long vybeId, Long commentId, Long userId) {
        int rowsDeleted =
                commentRepository.deleteByCommentIdAndVybeIdAndUserId(commentId, vybeId, userId);
        return rowsDeleted > 0;
    }

    @Transactional
    public List<Comment> getCommentsByVybeId(Long vybeId) {
        return commentRepository.findByVybeId(vybeId);
    }

    @Transactional
    public Like saveLike(Like like) {

        if (!likeRepository
                .findByVybeIdAndUser_UserId(like.getVybe().getId(), like.getUser().getUserId())
                .isEmpty()) {
            return null;
        }

        return likeRepository.save(like);
    }

    @Transactional
    public Like deleteLike(Long vybeId, Long userId) {
        return likeRepository.deleteByVybeIdAndUser_UserId(vybeId, userId).stream()
                .findFirst()
                .orElseThrow();
    }

    @Transactional
    public Like saveCommentLike(Like like) {

        if (likeRepository.findByCommentIdAndUserId(
                        like.getComment().getId(), like.getUser().getUserId())
                != null) {
            return null;
        }

        return likeRepository.save(like);
    }

    @Transactional
    public boolean deleteCommentLike(Long commentId, Long userId) {
        int rowsDeleted = likeRepository.deleteByCommentIdAndUserId(commentId, userId);
        return rowsDeleted > 0;
    }

    @Transactional
    public List<Like> getLikesByVybeId(Long vybeId) {
        return likeRepository.findByVybeId(vybeId);
    }
}
