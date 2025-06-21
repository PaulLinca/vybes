package com.vybes.controller;

import com.vybes.dto.request.FeedbackRequestDTO;
import com.vybes.exception.UserNotFoundException;
import com.vybes.model.Feedback;
import com.vybes.model.VybesUser;
import com.vybes.repository.FeedbackRepository;
import com.vybes.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;

@RestController
@RequestMapping("/api/feedback")
@RequiredArgsConstructor
public class FeedbackController {

    private final FeedbackRepository feedbackRepository;
    private final UserRepository userRepository;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/submit")
    public void setUsername(@RequestBody FeedbackRequestDTO request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Feedback feedback =
                Feedback.builder()
                        .text(request.getText())
                        .postedDate(ZonedDateTime.now())
                        .user(getUser(authentication.getName()))
                        .build();

        feedbackRepository.save(feedback);
    }

    private VybesUser getUser(String name) {
        return userRepository
                .findByEmail(name)
                .orElseThrow(() -> new UserNotFoundException("User not found: " + name));
    }
}
