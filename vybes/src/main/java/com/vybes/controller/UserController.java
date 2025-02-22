package com.vybes.controller;

import com.vybes.dto.UsernameSetupRequestDTO;
import com.vybes.dto.VybesUserResponseDTO;
import com.vybes.service.user.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/user")
@CrossOrigin("*") // check what this means
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;

    @ResponseStatus(HttpStatus.CREATED)
    @GetMapping
    public VybesUserResponseDTO getUser(@RequestParam String username) {
        return userService.setUsername(username);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/setUsername")
    public VybesUserResponseDTO setUsername(@RequestBody UsernameSetupRequestDTO usernameSetupRequestDTO) {
        return userService.setUsername(usernameSetupRequestDTO.getUsername());
    }
}
