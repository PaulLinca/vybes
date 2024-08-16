package com.vybes.service.vybe;

import com.vybes.service.vybe.entity.Comment;
import com.vybes.service.vybe.entity.Like;
import com.vybes.service.vybe.entity.Vybe;
import com.vybes.service.vybe.repository.CommentRepository;
import com.vybes.service.vybe.repository.LikeRepository;
import com.vybes.service.vybe.repository.VybeRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VybeService {
    private final VybeRepository vybeRepository;
    private final LikeRepository likeRepository;
    private final CommentRepository commentRepository;

    public Vybe createVybe(Vybe vybe) {
        vybeRepository.save(vybe);
        return vybe;
    }

    public List<Vybe> getAllVybes() {
        return vybeRepository.findAll();
    }

    public Vybe getVybeById(Long id) {
        return vybeRepository.findById(id).orElseThrow();
    }

    public Comment saveComment(Comment comment) {
        return commentRepository.save(comment);
    }

    public List<Comment> getCommentsByVybeId(Long vybeId) {
        return commentRepository.findByVybeId(vybeId);
    }

    public Like saveLike(Like like) {
        return likeRepository.save(like);
    }

    public List<Like> getLikesByVybeId(Long vybeId) {
        return likeRepository.findByVybeId(vybeId);
    }
}
