package com.vybes.controller;

import com.vybes.service.user.repository.UserRepository;
import com.vybes.service.vybe.VybeService;
import com.vybes.service.vybe.entity.Vybe;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/vybes")
@RequiredArgsConstructor
public class VybesController {

    private final VybeService vybeService;
    private final UserRepository userRepository;

    @GetMapping(value = "/findAll", produces = "application/json; charset=UTF-8")
    public List<Vybe> getAllVybes() {
        return vybeService.getAllVybes();
    }

    @PostMapping(value = "/post", produces = "application/json; charset=UTF-8")
    public Vybe postVybe(Authentication authentication, @RequestBody Vybe vybe) {
        userRepository.findByUsername(authentication.getName()).ifPresent(vybe::setUser);

        return vybeService.postVybe(vybe);
    }
}
