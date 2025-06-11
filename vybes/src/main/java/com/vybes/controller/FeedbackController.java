package com.vybes.controller;

import com.vybes.dto.request.FeedbackRequestDTO;
import com.vybes.model.Feedback;
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
                        .user(userRepository.findByEmail(authentication.getName()).orElseThrow())
                        .build();

        feedbackRepository.save(feedback);
    }
}
