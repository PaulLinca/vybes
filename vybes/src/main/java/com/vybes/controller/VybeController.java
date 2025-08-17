package com.vybes.controller;

import com.vybes.dto.VybeDTO;
import com.vybes.dto.mapper.PostMapper;
import com.vybes.dto.request.CreateVybeRequestDTO;
import com.vybes.exception.UserNotFoundException;
import com.vybes.model.Vybe;
import com.vybes.model.VybesUser;
import com.vybes.repository.UserRepository;
import com.vybes.service.post.PostService;
import com.vybes.service.post.VybeService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/vybes")
@RequiredArgsConstructor
public class VybeController {

    private final VybeService vybeService;
    private final UserRepository userRepository;
    private final PostMapper postMapper;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/post", produces = "application/json; charset=UTF-8")
    public VybeDTO postVybe(@RequestBody CreateVybeRequestDTO request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Vybe vybe = vybeService.createVybe(request, getUser(authentication.getName()));

        return postMapper.transformToDTO(vybe);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{vybeId}", produces = "application/json; charset=UTF-8")
    public VybeDTO getVybe(@PathVariable Long vybeId) {
        Vybe vybe = vybeService.getVybe(vybeId);

        return postMapper.transformToDTO(vybe);
    }

    private VybesUser getUser(String name) {
        return userRepository
                .findByEmail(name)
                .orElseThrow(() -> new UserNotFoundException("User not found: " + name));
    }
}
