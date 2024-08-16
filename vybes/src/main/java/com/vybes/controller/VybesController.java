package com.vybes.controller;

import com.vybes.dto.CreateVybeRequestDTO;
import com.vybes.service.user.repository.UserRepository;
import com.vybes.service.vybe.VybeService;
import com.vybes.service.vybe.dto.CommentDTO;
import com.vybes.service.vybe.dto.LikeDTO;
import com.vybes.service.vybe.dto.VybeDTO;
import com.vybes.service.vybe.entity.Comment;
import com.vybes.service.vybe.entity.Like;
import com.vybes.service.vybe.entity.Vybe;
import com.vybes.service.vybe.mapper.CommentMapper;
import com.vybes.service.vybe.mapper.LikeMapper;
import com.vybes.service.vybe.mapper.VybeMapper;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/vybes")
@RequiredArgsConstructor
public class VybesController {

    private final VybeService vybeService;
    private final UserRepository userRepository;
    private final VybeMapper vybeMapper;
    private final CommentMapper commentMapper;
    private final LikeMapper likeMapper;

    @GetMapping(value = "/findAll", produces = "application/json; charset=UTF-8")
    public List<VybeDTO> getAllVybes() {
        return vybeService.getAllVybes().stream().map(vybeMapper::transform).toList();
    }

    @PostMapping(value = "/post", produces = "application/json; charset=UTF-8")
    public VybeDTO postVybe(@RequestBody CreateVybeRequestDTO request) {
        Vybe vybe = vybeMapper.transform(request);
        vybe.setComments(new ArrayList<>());
        vybe.setLikes(new ArrayList<>());
        vybe.setUser(userRepository.findByUserId(request.getUserId()).orElseThrow());

        return vybeMapper.transform(vybeService.createVybe(vybe));
    }

    @PostMapping("/comments")
    public CommentDTO createComment(@RequestBody CommentDTO request) {
        Comment comment = commentMapper.transform(request);
        comment.setUser(userRepository.findByUserId(request.getUserId()).orElseThrow());
        comment.setVybe(vybeService.getVybeById(request.getUserId()));

        return commentMapper.transform(vybeService.saveComment(comment));
    }

    @GetMapping("/{vybeId}/comments")
    public List<CommentDTO> getCommentsByPostId(@PathVariable Long vybeId) {
        return vybeService.getCommentsByVybeId(vybeId).stream()
                .map(commentMapper::transform)
                .toList();
    }

    @PostMapping("/likes")
    public LikeDTO createLike(@RequestBody LikeDTO request) {
        return likeMapper.transform(
                vybeService.saveLike(
                        Like.builder()
                                .user(
                                        userRepository
                                                .findByUserId(request.getUserId())
                                                .orElseThrow())
                                .vybe(vybeService.getVybeById(request.getVybeId()))
                                .build()));
    }

    @GetMapping("/{vybeId}/likes")
    public List<LikeDTO> getLikesByVybeId(@PathVariable Long vybeId) {
        return vybeService.getLikesByVybeId(vybeId).stream().map(likeMapper::transform).toList();
    }
}
