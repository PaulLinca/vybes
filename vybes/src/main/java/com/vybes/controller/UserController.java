package com.vybes.controller;

import com.vybes.dto.FavoritesUpdateRequest;
import com.vybes.dto.UsernameSetupRequestDTO;
import com.vybes.dto.VybesUserResponseDTO;
import com.vybes.service.user.UserFavoritesService;
import com.vybes.service.user.UserService;

import com.vybes.service.user.model.VybesUser;
import com.vybes.service.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/user")
@CrossOrigin("*") // check what this means
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;
    private final UserFavoritesService userFavoritesService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public VybesUserResponseDTO getUser(@RequestParam String username) {
        return userService.getByUsername(username);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/setUsername")
    public VybesUserResponseDTO setUsername(@RequestBody UsernameSetupRequestDTO usernameSetupRequestDTO) {
        return userService.setUsername(usernameSetupRequestDTO.getUsername());
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/updateFavorites")
    public void updateFavorites(@RequestBody FavoritesUpdateRequest favoritesUpdateRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        VybesUser user = userRepository.findByEmail(authentication.getName()).orElseThrow();

        userFavoritesService.updateUserFavorites(user, favoritesUpdateRequest);

        userRepository.save(user);
    }
}
