package com.vybes.controller;

import com.vybes.dto.CommentDTO;
import com.vybes.dto.LikeDTO;
import com.vybes.dto.response.PageResponse;
import com.vybes.dto.VybeDTO;
import com.vybes.dto.mapper.CommentMapper;
import com.vybes.dto.mapper.LikeMapper;
import com.vybes.dto.mapper.VybeMapper;
import com.vybes.dto.request.CreateVybeRequestDTO;
import com.vybes.external.spotify.SpotifyService;
import com.vybes.external.spotify.model.entity.SpotifyTrack;
import com.vybes.model.VybesUser;
import com.vybes.repository.ArtistRepository;
import com.vybes.repository.UserRepository;
import com.vybes.service.post.VybeService;
import com.vybes.model.Artist;
import com.vybes.model.Comment;
import com.vybes.model.Like;
import com.vybes.model.Vybe;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/vybes")
@RequiredArgsConstructor
public class VybesController {

    private final VybeService vybeService;
    private final SpotifyService spotifyService;
    private final UserRepository userRepository;
    private final ArtistRepository artistRepository;
    private final VybeMapper vybeMapper;
    private final CommentMapper commentMapper;
    private final LikeMapper likeMapper;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(produces = "application/json; charset=UTF-8")
    public ResponseEntity<PageResponse<VybeDTO>> getVybesPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sort,
            @RequestParam(defaultValue = "DESC") String direction) {

        Page<Vybe> vybesPage = vybeService.getVybesPaginated(page, size, sort, direction);

        List<VybeDTO> vybesDTOs =
                vybesPage.getContent().stream().map(vybeMapper::transform).toList();

        PageResponse<VybeDTO> response =
                new PageResponse<>(
                        vybesDTOs,
                        vybesPage.getNumber(),
                        vybesPage.getSize(),
                        vybesPage.getTotalElements(),
                        vybesPage.getTotalPages(),
                        vybesPage.isLast());

        return ResponseEntity.ok(response);
    }

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
        vybe.setUser(userRepository.findByEmail(authentication.getName()).orElseThrow());

        SpotifyTrack spotifyTrack = spotifyService.getTrack(request.getSpotifyTrackId());
        vybe.setSpotifyTrackId(spotifyTrack.getId());
        vybe.setSongName(spotifyTrack.getName());
        vybe.setSpotifyAlbumId(spotifyTrack.getAlbum().getId());
        vybe.setImageUrl(spotifyTrack.getAlbum().getImageUrl());
        vybe.setSpotifyArtists(
                spotifyTrack.getArtists().stream()
                        .map(
                                a ->
                                        Optional.ofNullable(
                                                        artistRepository.findBySpotifyId(a.getId()))
                                                .orElseGet(
                                                        () ->
                                                                artistRepository.save(
                                                                        Artist.builder()
                                                                                .spotifyId(
                                                                                        a.getId())
                                                                                .name(a.getName())
                                                                                .build())))
                        .toList());

        return vybeMapper.transform(vybeService.createVybe(vybe));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{vybeId}/likes")
    public ResponseEntity<LikeDTO> likeVybe(@PathVariable Long vybeId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Vybe vybe = vybeService.getVybeById(vybeId);
        if (vybe.getUser().getUsername().equals(authentication.getName())) {
            return ResponseEntity.badRequest().build();
        }

        Like like =
                Like.builder()
                        .user(userRepository.findByEmail(authentication.getName()).orElseThrow())
                        .vybe(vybe)
                        .build();

        return Optional.ofNullable(vybeService.saveLike(like))
                .map(likeMapper::transform)
                .map(l -> new ResponseEntity<>(l, HttpStatus.CREATED))
                .orElse(ResponseEntity.badRequest().build());
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{vybeId}/likes")
    public LikeDTO unlikeVybe(@PathVariable Long vybeId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return likeMapper.transform(
                vybeService.deleteLike(
                        vybeId,
                        userRepository
                                .findByEmail(authentication.getName())
                                .map(VybesUser::getUserId)
                                .orElseThrow()));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{vybeId}/comments")
    public CommentDTO createComment(@PathVariable Long vybeId, @RequestBody CommentDTO request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Comment comment = commentMapper.transform(request);
        comment.setUser(userRepository.findByEmail(authentication.getName()).orElseThrow());
        comment.setVybe(vybeService.getVybeById(vybeId));

        return commentMapper.transform(vybeService.saveComment(comment));
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{vybeId}/comments/{commentId}")
    public ResponseEntity<?> deleteComment(
            @PathVariable Long vybeId, @PathVariable Long commentId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        boolean isDeleted =
                vybeService.deleteComment(
                        vybeId,
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
    @PostMapping("/{vybeId}/comments/{commentId}/likes")
    public ResponseEntity<LikeDTO> likeComment(
            @PathVariable Long vybeId, @PathVariable Long commentId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Like like =
                Like.builder()
                        .user(userRepository.findByEmail(authentication.getName()).orElseThrow())
                        .comment(vybeService.getCommentById(commentId))
                        .build();

        return Optional.ofNullable(vybeService.saveCommentLike(like))
                .map(likeMapper::transform)
                .map(l -> new ResponseEntity<>(l, HttpStatus.CREATED))
                .orElse(ResponseEntity.badRequest().build());
    }

    @ResponseStatus(HttpStatus.CREATED)
    @DeleteMapping("/{vybeId}/comments/{commentId}/likes")
    public ResponseEntity<?> unlikeComment(
            @PathVariable Long vybeId, @PathVariable Long commentId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Long userId =
                userRepository
                        .findByEmail(authentication.getName())
                        .map(VybesUser::getUserId)
                        .orElseThrow();

        boolean isDeleted = vybeService.deleteCommentLike(commentId, userId);
        if (isDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
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
