package com.karolrinc.books.api.authentication;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice("com.karolrinc.books.api.authorization")
@Slf4j
class AuthorizationExceptionHandler {
    private static final String DEFAULT_ERROR_MSG = "Something went wrong. Please contact support.";

    @ExceptionHandler(UserDisabledException.class)
    public ResponseEntity<?> handleUserDisabledException(UserDisabledException e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(UserDisabledException.DEFAULT_MSG, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidUserCredentialsException.class)
    public ResponseEntity<?> handleInvalidUserCredentialsException(InvalidUserCredentialsException e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(InvalidUserCredentialsException.DEFAULT_MSG, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(DEFAULT_ERROR_MSG, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
