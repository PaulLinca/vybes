package com.vybes.controller;

import com.vybes.dto.LoginResponseDTO;
import com.vybes.dto.RegistrationDTO;
import com.vybes.security.model.ApplicationUser;
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
    public ApplicationUser registerUser(@RequestBody RegistrationDTO registrationDTO) {
        return authenticationService.registerUser(
                registrationDTO.getUsername(), registrationDTO.getPassword());
    }

    @PostMapping("/login")
    public LoginResponseDTO loginUser(@RequestBody RegistrationDTO registrationDTO) {
        return authenticationService.loginUser(
                registrationDTO.getUsername(), registrationDTO.getPassword());
    }
}
