package com.vybes.exception.handler;

import com.vybes.dto.response.ErrorResponse;
import com.vybes.exception.EmailAlreadyUsedException;
import com.vybes.exception.InvalidCredentialsException;
import com.vybes.exception.InvalidRequestException;
import com.vybes.exception.UserAlreadyExistsException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleUserAlreadyExistsException(
            UserAlreadyExistsException ex) {
        logger.error("User already exists", ex);

        return new ResponseEntity<>(
                ErrorResponse.builder().message("User already exists").status(409).build(),
                HttpStatus.CONFLICT);
    }

    @ExceptionHandler(EmailAlreadyUsedException.class)
    public ResponseEntity<ErrorResponse> handleEmailAlreadyUsedException(
            UserAlreadyExistsException ex) {
        logger.error("Email address already in use", ex);

        return new ResponseEntity<>(
                ErrorResponse.builder().message("Email address already in use").status(409).build(),
                HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<ErrorResponse> handleInvalidCredentialsException(
            InvalidCredentialsException ex) {
        logger.error("Unhandled exception occurred", ex);

        return new ResponseEntity<>(
                ErrorResponse.builder().message("Invalid credentials").status(401).build(),
                HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<ErrorResponse> handleBadCredentialsException(BadCredentialsException ex) {
        logger.error("Unhandled exception occurred", ex);

        return new ResponseEntity<>(
                ErrorResponse.builder().message("Invalid username or password").status(401).build(),
                HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(InvalidRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleInvalidRequestException(InvalidRequestException ex) {
        logger.error("Invalid request", ex);

        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGlobalException(Exception ex, WebRequest request) {
        logger.error("Unhandled exception occurred", ex);

        return new ResponseEntity<>(
                "An unexpected error occurred: " + ex.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
