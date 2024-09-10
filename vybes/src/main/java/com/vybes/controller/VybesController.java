package com.vybes.controller;

import com.vybes.dto.CreateVybeRequestDTO;
import com.vybes.service.user.model.VybesUser;
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

import org.springframework.http.HttpStatus;
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

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/findAll", produces = "application/json; charset=UTF-8")
    public List<VybeDTO> getAllVybes() {
        return vybeService.getAllVybes().stream().map(vybeMapper::transform).toList();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{vybeId}", produces = "application/json; charset=UTF-8")
    public VybeDTO getVybe(@PathVariable Long vybeId) {
        return vybeMapper.transform(vybeService.getVybeById(vybeId));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/post", produces = "application/json; charset=UTF-8")
    public VybeDTO postVybe(@RequestBody CreateVybeRequestDTO request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Vybe vybe = vybeMapper.transform(request);
        vybe.setComments(new ArrayList<>());
        vybe.setLikes(new ArrayList<>());
        vybe.setUser(userRepository.findByUsername(authentication.getName()).orElseThrow());

        return vybeMapper.transform(vybeService.createVybe(vybe));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{vybeId}/likes")
    public LikeDTO likeVybe(@PathVariable Long vybeId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return likeMapper.transform(
                vybeService.saveLike(
                        Like.builder()
                                .user(
                                        userRepository
                                                .findByUsername(authentication.getName())
                                                .orElseThrow())
                                .vybe(vybeService.getVybeById(vybeId))
                                .build()));
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{vybeId}/likes")
    public LikeDTO unlikeVybe(@PathVariable Long vybeId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return likeMapper.transform(
                vybeService.deleteLike(
                        vybeId,
                        userRepository
                                .findByUsername(authentication.getName())
                                .map(VybesUser::getUserId)
                                .orElseThrow()));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{vybeId}/comments")
    public CommentDTO createComment(@PathVariable Long vybeId, @RequestBody CommentDTO request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Comment comment = commentMapper.transform(request);
        comment.setUser(userRepository.findByUsername(authentication.getName()).orElseThrow());
        comment.setVybe(vybeService.getVybeById(vybeId));

        return commentMapper.transform(vybeService.saveComment(comment));
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{vybeId}/comments")
    public CommentDTO deleteComment(@PathVariable Long vybeId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return commentMapper.transform(
                vybeService.deleteComment(
                        vybeId,
                        userRepository
                                .findByUsername(authentication.getName())
                                .map(VybesUser::getUserId)
                                .orElseThrow()));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{vybeId}/comments")
    public List<CommentDTO> getCommentsByPostId(@PathVariable Long vybeId) {
        return vybeService.getCommentsByVybeId(vybeId).stream()
                .map(commentMapper::transform)
                .toList();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{vybeId}/likes")
    public List<LikeDTO> getLikesByVybeId(@PathVariable Long vybeId) {
        return vybeService.getLikesByVybeId(vybeId).stream().map(likeMapper::transform).toList();
    }
}
