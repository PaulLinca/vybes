package com.vybes.service.vybe;

import com.vybes.service.vybe.entity.Comment;
import com.vybes.service.vybe.entity.Like;
import com.vybes.service.vybe.entity.Vybe;
import com.vybes.service.vybe.repository.CommentRepository;
import com.vybes.service.vybe.repository.LikeRepository;
import com.vybes.service.vybe.repository.VybeRepository;

import jakarta.transaction.Transactional;

import lombok.RequiredArgsConstructor;

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
    public List<Vybe> getAllVybes() {
        return vybeRepository.findAll();
    }

    @Transactional
    public Vybe getVybeById(Long id) {
        return vybeRepository.findById(id).orElseThrow();
    }

    @Transactional
    public Comment saveComment(Comment comment) {
        return commentRepository.save(comment);
    }

    @Transactional
    public boolean deleteComment(Long vybeId, Long commentId, Long userId) {
        int rowsDeleted = commentRepository.deleteByCommentIdAndVybeIdAndUserId(commentId, vybeId, userId);
        return rowsDeleted > 0;
    }

    @Transactional
    public List<Comment> getCommentsByVybeId(Long vybeId) {
        return commentRepository.findByVybeId(vybeId);
    }

    @Transactional
    public Like saveLike(Like like) {
        return likeRepository.save(like);
    }

    @Transactional
    public Like deleteLike(Long vybeId, Long userId) {
        return likeRepository.deleteByVybeIdAndUser_UserId(vybeId, userId).stream().findFirst().orElseThrow();
    }

    @Transactional
    public List<Like> getLikesByVybeId(Long vybeId) {
        return likeRepository.findByVybeId(vybeId);
    }
}
