package com.karolrinc.books.api.authentication;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice("com.karolrinc.books.api.authorization")
@Slf4j
class AuthenticationExceptionHandler {

    @ExceptionHandler(UserDisabledException.class)
    public ResponseEntity<?> handleUserDisabledException(UserDisabledException e) {
        log.error(e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                             .body(UserDisabledException.DEFAULT_MSG);
    }

    @ExceptionHandler(InvalidUserCredentialsException.class)
    public ResponseEntity<?> handleInvalidUserCredentialsException(InvalidUserCredentialsException e) {
        log.error(e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                             .body(InvalidUserCredentialsException.DEFAULT_MSG);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception e) {
        log.error(e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                             .body("Something went wrong. Please contact support.");
    }
}
