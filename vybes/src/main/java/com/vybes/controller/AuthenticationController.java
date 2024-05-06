package com.vybes.controller;

import com.vybes.dto.LoginResponseDTO;
import com.vybes.dto.RegistrationRequestDTO;
import com.vybes.service.user.model.VybesUser;
import com.vybes.service.auth.AuthenticationService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
@CrossOrigin("*") // check what this means
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public VybesUser registerUser(@RequestBody RegistrationRequestDTO registrationRequestDTO) {
        return authenticationService.registerUser(
                registrationRequestDTO.getUsername(), registrationRequestDTO.getPassword());
    }

    @PostMapping("/login")
    public LoginResponseDTO loginUser(@RequestBody RegistrationRequestDTO registrationRequestDTO) {
        return authenticationService.loginUser(
                registrationRequestDTO.getUsername(), registrationRequestDTO.getPassword());
    }
}
